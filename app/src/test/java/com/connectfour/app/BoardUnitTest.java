package com.connectfour.app;

import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.connectfour.app.model.Disk.EMPTY;
import static com.connectfour.app.model.Disk.PLAYER_1;
import static com.connectfour.app.model.Disk.PLAYER_2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BoardUnitTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board(2, 2);
    }

    @Test
    public void shouldInitializeEmptyBoard() {
        Board customBoard = new Board(2, 3);
        Disk[][] expectedValues = {{EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}};

        assertTrue(Arrays.deepEquals(customBoard.getValues(), expectedValues));
    }

    @Test
    public void shouldReturnTrueOnInsert_whenColumnIsNotFull() {
        assertTrue(board.insertIntoColumn(0));
    }

    @Test
    public void shouldReturnFalseOnInsert_whenColumnIsFull() {
        board.insertIntoColumn(0);
        board.insertIntoColumn(0);
        assertFalse(board.insertIntoColumn(0));
    }

    @Test
    public void shouldInsertValueIntoFirstEmptyIndex() {
        Disk[][] expectedValues = {{PLAYER_1, EMPTY}, {EMPTY, EMPTY}};

        board.insertIntoColumn(0, PLAYER_1);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldRemoveTopDisk() {
        Disk[][] expectedValues = {{EMPTY, EMPTY}, {EMPTY, EMPTY}};

        board.insertIntoColumn(0, PLAYER_1);
        board.removeTopDiskFromColumn(0);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldRemoveTopDisk_whenColumnIsFull() {
        Disk[][] expectedValues = {{PLAYER_1, EMPTY}, {EMPTY, EMPTY}};

        board.insertIntoColumn(0, PLAYER_1);
        board.insertIntoColumn(0, PLAYER_1);
        board.removeTopDiskFromColumn(0);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldNotRemoveTopDisk_whenColumnIsEmpty() {
        Disk[][] expectedValues = {{EMPTY, EMPTY}, {EMPTY, EMPTY}};

        board.removeTopDiskFromColumn(0);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldChangePlayer() {
        Disk previous = board.getCurrentPlayerDisk();
        board.changePlayer();

        assertNotEquals(previous, board.getCurrentPlayerDisk());
    }

    @Test
    public void shouldInsertCurrentPlayer() {
        Disk player1 = board.getCurrentPlayerDisk();
        board.insertIntoColumn(0);
        board.changePlayer();
        Disk player2 = board.getCurrentPlayerDisk();
        board.insertIntoColumn(0);

        Disk[][] expectedValues = {{player1, player2}, {EMPTY, EMPTY}};

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldCheckIfIsColumnFull() {
        board.insertIntoColumn(0);
        assertTrue(board.canInsertInColumn(0));

        board.insertIntoColumn(0);
        assertFalse(board.canInsertInColumn(0));
    }

    @Test
    public void shouldResetBoard() {
        Disk[][] expectedValues = {{EMPTY, EMPTY}, {EMPTY, EMPTY}};

        board.insertIntoColumn(0);
        board.insertIntoColumn(0);
        board.insertIntoColumn(1);
        board.insertIntoColumn(1);
        board.reset();

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldCheckIfBoardIsFull() {
        board.insertIntoColumn(0);
        assertFalse(board.isFull());

        board.insertIntoColumn(0);
        assertFalse(board.isFull());

        board.insertIntoColumn(1);
        assertFalse(board.isFull());

        board.insertIntoColumn(1);
        assertTrue(board.isFull());
    }

    @Test
    public void shouldCheckIfWonGame_whenBoardIsEmpty() {
        Board defaultBoard = new Board();

        assertFalse(defaultBoard.currentPlayerWonGame());
    }

    @Test
    public void shouldCheckIfWonGame_whenSequenceIsHorizontal() {
        Board defaultBoard = new Board();
        defaultBoard.insertIntoColumn(0);
        defaultBoard.insertIntoColumn(1);
        defaultBoard.insertIntoColumn(2);
        assertFalse(defaultBoard.currentPlayerWonGame());

        defaultBoard.insertIntoColumn(3);

        assertTrue(defaultBoard.currentPlayerWonGame());
    }

    @Test
    public void shouldCheckIfWonGame_whenSequenceIsVertical() {
        Board defaultBoard = new Board();
        defaultBoard.insertIntoColumn(0);
        defaultBoard.insertIntoColumn(0);
        defaultBoard.insertIntoColumn(0);
        assertFalse(defaultBoard.currentPlayerWonGame());

        defaultBoard.insertIntoColumn(0);

        assertTrue(defaultBoard.currentPlayerWonGame());
    }

    @Test
    public void shouldCheckIfWonGame_whenSequenceIsDiagonalPositiveSlope() {
        Board defaultBoard = new Board();
        defaultBoard.insertIntoColumn(0, PLAYER_1);
        defaultBoard.insertIntoColumn(1, PLAYER_2);
        defaultBoard.insertIntoColumn(1, PLAYER_1);
        defaultBoard.insertIntoColumn(2, PLAYER_2);
        defaultBoard.insertIntoColumn(2, PLAYER_2);
        defaultBoard.insertIntoColumn(2, PLAYER_1);
        defaultBoard.insertIntoColumn(3, PLAYER_2);
        defaultBoard.insertIntoColumn(3, PLAYER_2);
        defaultBoard.insertIntoColumn(3, PLAYER_2);
        assertFalse(defaultBoard.currentPlayerWonGame());

        defaultBoard.insertIntoColumn(3, PLAYER_1);

        assertTrue(defaultBoard.currentPlayerWonGame());
    }

    @Test
    public void shouldCheckIfWonGame_whenSequenceIsDiagonalNegativeSlope() {
        Board defaultBoard = new Board();
        defaultBoard.insertIntoColumn(3, PLAYER_1);
        defaultBoard.insertIntoColumn(2, PLAYER_2);
        defaultBoard.insertIntoColumn(2, PLAYER_1);
        defaultBoard.insertIntoColumn(1, PLAYER_2);
        defaultBoard.insertIntoColumn(1, PLAYER_2);
        defaultBoard.insertIntoColumn(1, PLAYER_1);
        defaultBoard.insertIntoColumn(0, PLAYER_2);
        defaultBoard.insertIntoColumn(0, PLAYER_2);
        defaultBoard.insertIntoColumn(0, PLAYER_2);
        assertFalse(defaultBoard.currentPlayerWonGame());

        defaultBoard.insertIntoColumn(0, PLAYER_1);

        assertTrue(defaultBoard.currentPlayerWonGame());
    }

    @Test
    public void shouldGenerateIdenticalHashCode_whenBoardsAreEmpty() {
        Board board1 = new Board();
        Board board2 = new Board();

        assertEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    public void shouldGenerateIdenticalHashCode_whenBoardsAreIdentical() {
        Board board = new Board();

        int[] columns = {0, 0, 1, 2, 1, 1, 2, 0, 6, 0, 6, 4, 2, 3, 2};

        for (int col : columns) {
            board.insertIntoColumn(col);
            board.changePlayer();
        }

        Board boardCopy = new Board(board);

        assertEquals(board.hashCode(), boardCopy.hashCode());
    }

    @Test
    public void shouldGenerateDifferentHashCode_whenBoardsAreDifferent() {
        Board board = new Board();

        int[] columns = {0, 0, 1, 2, 1, 1, 2, 0, 6, 0, 6, 4, 2, 3, 2};

        for (int col : columns) {
            board.insertIntoColumn(col);
            board.changePlayer();
        }

        Board boardCopy = new Board(board);
        boardCopy.insertIntoColumn(0, PLAYER_1);

        assertNotEquals(board.hashCode(), boardCopy.hashCode());
    }
}