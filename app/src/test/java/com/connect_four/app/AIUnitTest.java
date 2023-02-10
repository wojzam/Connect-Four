package com.connect_four.app;

import com.connect_four.app.ai.AI;
import com.connect_four.app.model.Board;
import com.connect_four.app.model.Disk;

import org.junit.Before;
import org.junit.Test;

import static com.connect_four.app.model.Disk.PLAYER_1;
import static com.connect_four.app.model.Disk.PLAYER_2;
import static org.junit.Assert.assertEquals;

public class AIUnitTest {

    private static final Disk AI_DISK = PLAYER_2;
    private static final Disk HUMAN_DISK = PLAYER_1;
    private final static int DEFAULT_DEPTH = 5;
    private Board board;
    private AI ai;

    @Before
    public void setUp() {
        board = new Board();
        board.changePlayer();
        ai = new AI(AI_DISK);
    }

    @Test
    public void shouldChooseWinningColumn_whenSequenceIsHorizontal() {
        board.insertIntoColumn(0, AI_DISK);
        board.insertIntoColumn(1, AI_DISK);
        board.insertIntoColumn(2, AI_DISK);

        assertEquals(3, ai.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldBlockOpponentWin_whenSequenceIsHorizontal() {
        board.insertIntoColumn(0, HUMAN_DISK);
        board.insertIntoColumn(1, HUMAN_DISK);
        board.insertIntoColumn(2, HUMAN_DISK);

        assertEquals(3, ai.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldChooseWinningColumn_whenSequenceIsVertical() {
        board.insertIntoColumn(0, AI_DISK);
        board.insertIntoColumn(0, AI_DISK);
        board.insertIntoColumn(0, AI_DISK);

        assertEquals(0, ai.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldBlockOpponentWin_whenSequenceIsVertical() {
        board.insertIntoColumn(0, HUMAN_DISK);
        board.insertIntoColumn(0, HUMAN_DISK);
        board.insertIntoColumn(0, HUMAN_DISK);

        assertEquals(0, ai.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldChooseWinningColumn_whenSequenceIsDiagonalPositiveSlope() {
        board.insertIntoColumn(0, AI_DISK);
        board.insertIntoColumn(1, HUMAN_DISK);
        board.insertIntoColumn(1, AI_DISK);
        board.insertIntoColumn(2, AI_DISK);
        board.insertIntoColumn(2, HUMAN_DISK);
        board.insertIntoColumn(2, AI_DISK);
        board.insertIntoColumn(3, HUMAN_DISK);
        board.insertIntoColumn(3, AI_DISK);
        board.insertIntoColumn(3, HUMAN_DISK);

        assertEquals(3, ai.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldBlockOpponentWin_whenSequenceIsDiagonalPositiveSlope() {
        board.insertIntoColumn(0, HUMAN_DISK);
        board.insertIntoColumn(1, AI_DISK);
        board.insertIntoColumn(1, HUMAN_DISK);
        board.insertIntoColumn(2, HUMAN_DISK);
        board.insertIntoColumn(2, AI_DISK);
        board.insertIntoColumn(2, HUMAN_DISK);
        board.insertIntoColumn(3, AI_DISK);
        board.insertIntoColumn(3, HUMAN_DISK);
        board.insertIntoColumn(3, AI_DISK);

        assertEquals(3, ai.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldChooseWinningColumn_whenSequenceIsDiagonalNegativeSlope() {
        board.insertIntoColumn(3, AI_DISK);
        board.insertIntoColumn(2, HUMAN_DISK);
        board.insertIntoColumn(2, AI_DISK);
        board.insertIntoColumn(1, AI_DISK);
        board.insertIntoColumn(1, HUMAN_DISK);
        board.insertIntoColumn(1, AI_DISK);
        board.insertIntoColumn(0, HUMAN_DISK);
        board.insertIntoColumn(0, AI_DISK);
        board.insertIntoColumn(0, HUMAN_DISK);

        assertEquals(0, ai.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldBlockOpponentWin_whenSequenceIsDiagonalNegativeSlope() {
        board.insertIntoColumn(3, HUMAN_DISK);
        board.insertIntoColumn(2, AI_DISK);
        board.insertIntoColumn(2, HUMAN_DISK);
        board.insertIntoColumn(1, HUMAN_DISK);
        board.insertIntoColumn(1, AI_DISK);
        board.insertIntoColumn(1, HUMAN_DISK);
        board.insertIntoColumn(0, AI_DISK);
        board.insertIntoColumn(0, HUMAN_DISK);
        board.insertIntoColumn(0, AI_DISK);

        assertEquals(0, ai.chooseColumn(board, DEFAULT_DEPTH));
    }

    @Test
    public void shouldPrioritizeWinning_whenPossible() {
        board.insertIntoColumn(0, AI_DISK);
        board.insertIntoColumn(0, AI_DISK);
        board.insertIntoColumn(0, AI_DISK);

        board.insertIntoColumn(1, HUMAN_DISK);
        board.insertIntoColumn(1, HUMAN_DISK);
        board.insertIntoColumn(1, HUMAN_DISK);

        assertEquals(0, ai.chooseColumn(board, DEFAULT_DEPTH));
    }

}
