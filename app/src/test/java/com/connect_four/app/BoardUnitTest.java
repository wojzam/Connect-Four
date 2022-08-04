package com.connect_four.app;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.connect_four.app.Board.EMPTY;
import static com.connect_four.app.Board.PLAYER_1;
import static com.connect_four.app.Board.PLAYER_2;
import static org.junit.Assert.assertFalse;
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
        byte[][] expectedValues = {{EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}};

        assertTrue(Arrays.deepEquals(customBoard.getValues(), expectedValues));
    }

    @Test
    public void shouldInsertValueIntoFirstEmptyIndex() {
        byte[][] expectedValues = {{PLAYER_1, EMPTY}, {EMPTY, EMPTY}};

        board.insertIntoColumn(0, PLAYER_1);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldInsertReturnTrue_whenColumnIsNotFull() {
        assertTrue(board.insertIntoColumn(0, PLAYER_1));
    }

    @Test
    public void shouldPlayerInsertReturnTrue_whenColumnIsNotFull() {
        assertTrue(board.playerInsertIntoColumn(0));
    }

    @Test
    public void shouldInsertReturnFalse_whenColumnIsFull() {
        board.playerInsertIntoColumn(0);
        board.playerInsertIntoColumn(0);
        assertFalse(board.playerInsertIntoColumn(0));
    }

    @Test
    public void shouldPlayerInsertReturnFalse_whenColumnIsFull() {
        board.insertIntoColumn(0, PLAYER_1);
        board.insertIntoColumn(0, PLAYER_1);
        assertFalse(board.insertIntoColumn(0, PLAYER_1));
    }

    @Test
    public void shouldInsertPlayer_1First() {
        byte[][] expectedValues = {{PLAYER_1, EMPTY}, {EMPTY, EMPTY}};

        board.playerInsertIntoColumn(0);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldChangePlayer_afterPlayerInserted() {
        byte[][] expectedValues = {{PLAYER_1, PLAYER_2}, {EMPTY, EMPTY}};

        board.playerInsertIntoColumn(0);
        board.playerInsertIntoColumn(0);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldNotChangePlayer_whenInsertFailed() {
        byte[][] expectedValues = {{PLAYER_1, PLAYER_2}, {PLAYER_1, EMPTY}};

        board.playerInsertIntoColumn(0);
        board.playerInsertIntoColumn(0);
        board.playerInsertIntoColumn(0);
        board.playerInsertIntoColumn(1);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }
}