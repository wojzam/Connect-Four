package com.connect_four.app;

import com.connect_four.app.ai.AI;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AIUnitTest {

    private final static int DEFAULT_DEPTH = 5;
    private Board board;

    @Before
    public void setUp() {
        board = new Board();
        board.changePlayer();
    }

    @Test
    public void shouldChooseWinningColumn_whenSequenceIsHorizontal() {
        board.insertIntoColumn(0, AI.AI_DISK);
        board.insertIntoColumn(1, AI.AI_DISK);
        board.insertIntoColumn(2, AI.AI_DISK);

        assertEquals(3, AI.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldBlockOpponentWin_whenSequenceIsHorizontal() {
        board.insertIntoColumn(0, AI.HUMAN_DISK);
        board.insertIntoColumn(1, AI.HUMAN_DISK);
        board.insertIntoColumn(2, AI.HUMAN_DISK);

        assertEquals(3, AI.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldChooseWinningColumn_whenSequenceIsVertical() {
        board.insertIntoColumn(0, AI.AI_DISK);
        board.insertIntoColumn(0, AI.AI_DISK);
        board.insertIntoColumn(0, AI.AI_DISK);

        assertEquals(0, AI.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldBlockOpponentWin_whenSequenceIsVertical() {
        board.insertIntoColumn(0, AI.HUMAN_DISK);
        board.insertIntoColumn(0, AI.HUMAN_DISK);
        board.insertIntoColumn(0, AI.HUMAN_DISK);

        assertEquals(0, AI.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldChooseWinningColumn_whenSequenceIsDiagonalPositiveSlope() {
        board.insertIntoColumn(0, AI.AI_DISK);
        board.insertIntoColumn(1, AI.HUMAN_DISK);
        board.insertIntoColumn(1, AI.AI_DISK);
        board.insertIntoColumn(2, AI.AI_DISK);
        board.insertIntoColumn(2, AI.HUMAN_DISK);
        board.insertIntoColumn(2, AI.AI_DISK);
        board.insertIntoColumn(3, AI.HUMAN_DISK);
        board.insertIntoColumn(3, AI.AI_DISK);
        board.insertIntoColumn(3, AI.HUMAN_DISK);

        assertEquals(3, AI.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldBlockOpponentWin_whenSequenceIsDiagonalPositiveSlope() {
        board.insertIntoColumn(0, AI.HUMAN_DISK);
        board.insertIntoColumn(1, AI.AI_DISK);
        board.insertIntoColumn(1, AI.HUMAN_DISK);
        board.insertIntoColumn(2, AI.HUMAN_DISK);
        board.insertIntoColumn(2, AI.AI_DISK);
        board.insertIntoColumn(2, AI.HUMAN_DISK);
        board.insertIntoColumn(3, AI.AI_DISK);
        board.insertIntoColumn(3, AI.HUMAN_DISK);
        board.insertIntoColumn(3, AI.AI_DISK);

        assertEquals(3, AI.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldChooseWinningColumn_whenSequenceIsDiagonalNegativeSlope() {
        board.insertIntoColumn(3, AI.AI_DISK);
        board.insertIntoColumn(2, AI.HUMAN_DISK);
        board.insertIntoColumn(2, AI.AI_DISK);
        board.insertIntoColumn(1, AI.AI_DISK);
        board.insertIntoColumn(1, AI.HUMAN_DISK);
        board.insertIntoColumn(1, AI.AI_DISK);
        board.insertIntoColumn(0, AI.HUMAN_DISK);
        board.insertIntoColumn(0, AI.AI_DISK);
        board.insertIntoColumn(0, AI.HUMAN_DISK);

        assertEquals(0, AI.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldBlockOpponentWin_whenSequenceIsDiagonalNegativeSlope() {
        board.insertIntoColumn(3, AI.HUMAN_DISK);
        board.insertIntoColumn(2, AI.AI_DISK);
        board.insertIntoColumn(2, AI.HUMAN_DISK);
        board.insertIntoColumn(1, AI.HUMAN_DISK);
        board.insertIntoColumn(1, AI.AI_DISK);
        board.insertIntoColumn(1, AI.HUMAN_DISK);
        board.insertIntoColumn(0, AI.AI_DISK);
        board.insertIntoColumn(0, AI.HUMAN_DISK);
        board.insertIntoColumn(0, AI.AI_DISK);

        assertEquals(0, AI.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldPrioritizeWinning_whenPossible() {
        board.insertIntoColumn(0, AI.AI_DISK);
        board.insertIntoColumn(0, AI.AI_DISK);
        board.insertIntoColumn(0, AI.AI_DISK);

        board.insertIntoColumn(1, AI.HUMAN_DISK);
        board.insertIntoColumn(1, AI.HUMAN_DISK);
        board.insertIntoColumn(1, AI.HUMAN_DISK);

        assertEquals(0, AI.chooseColumn(board, DEFAULT_DEPTH));
    }

}
