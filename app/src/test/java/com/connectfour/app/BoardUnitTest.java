package com.connectfour.app;

import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static com.connectfour.app.BoardTestData.EMPTY_BOARD;
import static com.connectfour.app.BoardTestData.FULL_BOARD;
import static com.connectfour.app.BoardTestData.HEIGHT;
import static com.connectfour.app.BoardTestData.WIDTH;
import static com.connectfour.app.model.Disk.EMPTY;
import static com.connectfour.app.model.Disk.PLAYER_1;
import static com.connectfour.app.model.Disk.PLAYER_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardUnitTest {

    private Board board;

    private static void fillUpColumn(Board board, int column) {
        for (int i = 0; i < board.getHeight(); i++) {
            board.insertIntoColumn(column);
        }
    }

    @BeforeEach
    public void setUp() {
        board = new Board(WIDTH, HEIGHT);
    }

    @Test
    public void shouldCreateEmptyBoard() {
        assertTrue(Arrays.deepEquals(board.getValues(), EMPTY_BOARD));
    }

    @Test
    public void shouldCreateBoardWithDimensions() {
        int width = 4, height = 6;
        Board customBoard = new Board(width, height);

        assertEquals(width, customBoard.getWidth());
        assertEquals(height, customBoard.getHeight());
    }

    @Test
    public void shouldCreateBoardFromArray() {
        Disk[][] values = {{PLAYER_1, EMPTY}, {PLAYER_2, EMPTY}};
        Board customBoard = new Board(values);

        assertTrue(Arrays.deepEquals(customBoard.getValues(), values));
    }

    @Test
    public void shouldCopyBoard() {
        Disk[][] values = {{PLAYER_1, EMPTY}, {PLAYER_2, EMPTY}};
        Board board1 = new Board(values);
        Board board2 = new Board(board1);

        board1.insertIntoColumn(0);

        assertTrue(Arrays.deepEquals(board2.getValues(), values));
    }

    @Test
    public void shouldResetBoard() {
        Board fullBoard = new Board(FULL_BOARD);

        fullBoard.reset();

        assertTrue(Arrays.deepEquals(fullBoard.getValues(), EMPTY_BOARD));
    }

    @Test
    public void shouldChangePlayer() {
        Disk previous = board.getCurrentPlayerDisk();

        board.changePlayer();

        assertNotEquals(previous, board.getCurrentPlayerDisk());
    }

    @Test
    public void shouldReturnTrueOnInsert_whenColumnIsNotFull() {
        assertTrue(board.insertIntoColumn(0));
    }

    @Test
    public void shouldReturnFalseOnInsert_whenColumnIsFull() {
        fillUpColumn(board, 0);

        assertFalse(board.insertIntoColumn(0));
    }

    @Test
    public void shouldInsertValueIntoFirstEmptyIndex() {
        Board customBoard = new Board(2, 2);

        customBoard.insertIntoColumn(0);

        Disk[][] expectedValues = {{PLAYER_1, EMPTY}, {EMPTY, EMPTY}};
        assertTrue(Arrays.deepEquals(customBoard.getValues(), expectedValues));
    }

    @Test
    public void shouldInsertCurrentPlayerDisk() {
        Disk player1 = board.getCurrentPlayerDisk();
        board.changePlayer();
        Disk player2 = board.getCurrentPlayerDisk();

        board.changePlayer();
        board.insertIntoColumn(0);
        board.changePlayer();
        board.insertIntoColumn(0);

        Disk[] expectedValues = {player1, player2, EMPTY, EMPTY};
        assertTrue(Arrays.deepEquals(board.getColumnValues(0), expectedValues));
    }

    @Test
    public void shouldRemoveTopDisk() {
        board.insertIntoColumn(0);
        board.removeTopDiskFromColumn(0);

        assertTrue(Arrays.deepEquals(board.getValues(), EMPTY_BOARD));
    }

    @Test
    public void shouldRemoveTopDisk_whenColumnIsFull() {
        Disk[][] initialValues = {{PLAYER_1, PLAYER_2}, {EMPTY, EMPTY}};
        Board customBoard = new Board(initialValues);

        customBoard.removeTopDiskFromColumn(0);

        Disk[][] expectedValues = {{PLAYER_1, EMPTY}, {EMPTY, EMPTY}};
        assertTrue(Arrays.deepEquals(customBoard.getValues(), expectedValues));
    }

    @Test
    public void shouldNotRemoveTopDisk_whenColumnIsEmpty() {
        board.removeTopDiskFromColumn(0);

        assertTrue(Arrays.deepEquals(board.getValues(), EMPTY_BOARD));
    }

    @Test
    void shouldGetAvailableColumns() {
        List<Integer> availableColumns = board.getAvailableColumns();

        List<Integer> expectedColumns = Arrays.asList(0, 1, 2, 3);
        assertTrue(availableColumns.containsAll(expectedColumns));
    }

    @Test
    void shouldGetAvailableColumns_whenNotAllColumnsAreAvailable() {
        fillUpColumn(board, 0);
        fillUpColumn(board, 3);

        List<Integer> availableColumns = board.getAvailableColumns();

        List<Integer> expectedColumns = Arrays.asList(1, 2);
        assertTrue(availableColumns.containsAll(expectedColumns));
    }

    @Test
    void shouldGetAvailableColumns_whenBoardIsFull() {
        Board fullBoard = new Board(FULL_BOARD);

        List<Integer> availableColumns = fullBoard.getAvailableColumns();

        assertTrue(availableColumns.isEmpty());
    }

    @Test
    public void shouldCheckIfBoardIsFull() {
        Board fullBoard = new Board(FULL_BOARD);

        assertTrue(fullBoard.isFull());
    }

    @Test
    public void shouldCheckIfBoardIsFull_whenBoardIsNotFull() {
        Board almostFullBoard = new Board(FULL_BOARD);
        almostFullBoard.removeTopDiskFromColumn(0);

        assertFalse(almostFullBoard.isFull());
    }

    @ParameterizedTest(name = "winning {2}")
    @MethodSource("com.connectfour.app.BoardTestData#providePotentialWinningSequences")
    public void shouldCheckIfWonGame_givenWinningSequence(Disk[][] values, int winningMove, String name) {
        Board board = new Board(values);
        board.insertIntoColumn(winningMove);

        assertTrue(board.currentPlayerWonGame(), name);
    }

    @ParameterizedTest(name = "non-winning {1}")
    @MethodSource("com.connectfour.app.BoardTestData#provideNonWinningSequences")
    public void shouldCheckIfWonGame_givenNonWinningSequence(Disk[][] values, String name) {
        Board board = new Board(values);

        assertFalse(board.currentPlayerWonGame(), name);
    }

    @Test
    public void shouldGenerateIdenticalHashCode_whenBoardsAreIdentical() {
        Board board1 = new Board();
        Board board2 = new Board();
        int[] columns = {0, 0, 1, 2, 1, 1, 2, 0, 6, 0, 6, 4, 2, 3, 2};
        for (int col : columns) {
            board1.insertIntoColumn(col);
            board2.insertIntoColumn(col);
            board1.changePlayer();
            board2.changePlayer();
        }

        assertEquals(board1.getBoardHash(), board2.getBoardHash());
    }

    @ParameterizedTest(name = "Different column {0}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
    public void shouldGenerateDifferentHashCode_whenBoardsAreDifferent(int differentColumn) {
        Board board1 = new Board();
        Board board2 = new Board();
        int[] columns = {0, 0, 1, 2, 1, 1, 2, 0, 6, 0, 6, 4, 2, 3, 2};
        for (int col : columns) {
            board1.insertIntoColumn(col);
            board2.insertIntoColumn(col);
            board1.changePlayer();
            board2.changePlayer();
        }

        board2.insertIntoColumn(differentColumn);

        assertNotEquals(board1.getBoardHash(), board2.getBoardHash());
    }
}