package com.connect_four.app;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.connect_four.app.Board.EMPTY;
import static com.connect_four.app.Board.PLAYER_1;
import static com.connect_four.app.Board.PLAYER_2;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class BoardUnitTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board(2, 2);
    }

    @Test
    public void shouldInitializeArray() {
        Board customBoard = new Board(2, 3);
        byte[][] expectedValues = {{EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}};

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
        byte[][] expectedValues = {{PLAYER_1, EMPTY}, {EMPTY, EMPTY}};

        board.insertIntoColumn(0, PLAYER_1);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldChangePlayer() {
        byte previous = board.getCurrentPlayerID();
        board.changePlayer();

        assertNotEquals(previous, board.getCurrentPlayerID());
    }

    @Test
    public void shouldInsertCurrentPlayer() {
        byte player1 = board.getCurrentPlayerID();
        board.insertIntoColumn(0);
        board.changePlayer();
        byte player2 = board.getCurrentPlayerID();
        board.insertIntoColumn(0);

        byte[][] expectedValues = {{player1, player2}, {EMPTY, EMPTY}};

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }
}