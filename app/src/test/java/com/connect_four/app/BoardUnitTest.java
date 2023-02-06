package com.connect_four.app;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.connect_four.app.Disk.EMPTY;
import static com.connect_four.app.Disk.PLAYER_1;
import static com.connect_four.app.Disk.PLAYER_2;
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
    public void shouldInitializeArray() {
        Board customBoard = new Board(2, 3);
        Disk[][] expectedValues = {{EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}};

        assertTrue(Arrays.deepEquals(customBoard.getValues(), expectedValues));
    }

    @Test
    public void shouldReturnTrue_whenColumnIsNotFull() {
        assertTrue(board.insertIntoColumn(0));
    }

    @Test
    public void shouldReturnFalse_whenColumnIsFull() {
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
        assertFalse(board.isColumnFull(0));

        board.insertIntoColumn(0);
        assertTrue(board.isColumnFull(0));
    }

    @Test
    public void shouldCheckIfIsFull() {
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
    public void shouldCheckIfWonGame_whenSequenceIsHorizontal() {
        Board defaultBoard = new Board();
        defaultBoard.insertIntoColumn(0);
        defaultBoard.insertIntoColumn(1);
        defaultBoard.insertIntoColumn(2);
        defaultBoard.insertIntoColumn(3);

        assertTrue(defaultBoard.currentPlayerWonGame());
    }

    @Test
    public void shouldCheckIfWonGame_whenSequenceIsVertical() {
        Board defaultBoard = new Board();
        defaultBoard.insertIntoColumn(0);
        defaultBoard.insertIntoColumn(0);
        defaultBoard.insertIntoColumn(0);
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
        defaultBoard.insertIntoColumn(0, PLAYER_1);

        assertTrue(defaultBoard.currentPlayerWonGame());
    }
}