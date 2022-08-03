package com.connectfour.app;

import org.junit.Test;

import java.util.Arrays;

import static com.connectfour.app.Board.EMPTY;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardUnitTest {

    @Test
    public void shouldInitializeArray() {
        Board board = new Board(2, 3);
        int[][] expectedValues = {{EMPTY, EMPTY}, {EMPTY, EMPTY}, {EMPTY, EMPTY}};

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldInsertValueIntoFirstEmptyIndex() {
        Board board = new Board(2, 2);
        int[][] expectedValues = {{EMPTY, EMPTY}, {1, EMPTY}};

        board.insertIntoColumn(0, 1);

        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    public void shouldNotInsertValue_whenColumnIsFull() {
        Board board = new Board(2, 2);

        assertTrue(board.insertIntoColumn(0, 1));
        assertTrue(board.insertIntoColumn(0, 1));
        assertFalse(board.insertIntoColumn(0, 1));
    }
}