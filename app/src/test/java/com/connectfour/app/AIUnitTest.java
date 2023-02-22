package com.connectfour.app;

import com.connectfour.app.ai.AI;
import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

import static com.connectfour.app.model.Disk.EMPTY;
import static com.connectfour.app.model.Disk.PLAYER_1;
import static com.connectfour.app.model.Disk.PLAYER_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIUnitTest {

    private static final int REPETITIONS_COUNT = 5;
    private final static int TEST_DEPTH = 2;
    private AI ai;

    @BeforeEach
    public void setUp() {
        ai = new AI();
    }

    @Test
    public void shouldThrowException_whenDepthIsNotPositiveNumber() {
        Board board = new Board();
        int invalidDepth = 0;

        assertThrows(AssertionError.class, () -> ai.chooseColumn(board, invalidDepth));
    }

    @RepeatedTest(REPETITIONS_COUNT)
    public void shouldAddToSequence() {
        Board board = new Board();
        board.insertIntoColumn(3);

        int chosenColumn = ai.chooseColumn(board, 1);
        List<Integer> bestMoves = Arrays.asList(2, 3, 4);
        assertTrue(bestMoves.contains(chosenColumn));
    }

    @RepeatedTest(REPETITIONS_COUNT)
    public void shouldBlockOpponentWinInAdvance() {
        Board board = new Board();
        board.insertIntoColumn(3);
        board.insertIntoColumn(4);
        board.changePlayer();

        int chosenColumn = ai.chooseColumn(board, 4);
        List<Integer> blockingMoves = Arrays.asList(2, 5);
        assertTrue(blockingMoves.contains(chosenColumn));
    }

    @RepeatedTest(REPETITIONS_COUNT)
    public void shouldPrioritizeWinning_whenPossible() {
        Disk[][] boardValues = {
                {PLAYER_1, PLAYER_1, PLAYER_1, EMPTY},
                {PLAYER_2, PLAYER_2, PLAYER_2, EMPTY}};
        Board board = new Board(boardValues);

        assertEquals(0, ai.chooseColumn(board, TEST_DEPTH));
    }

    @ParameterizedTest(name = "winning {2}")
    @MethodSource("com.connectfour.app.BoardTestData#providePotentialWinningSequences")
    public void shouldChooseWinningColumn(Disk[][] values, int bestMove, String name) {
        Board board = new Board(values);

        assertEquals(bestMove, ai.chooseColumn(board, TEST_DEPTH), name);
    }

    @ParameterizedTest(name = "block winning {2}")
    @MethodSource("com.connectfour.app.BoardTestData#providePotentialWinningSequences")
    public void shouldBlockOpponentWin(Disk[][] values, int bestMove, String name) {
        Board board = new Board(values);
        board.changePlayer();

        assertEquals(bestMove, ai.chooseColumn(board, TEST_DEPTH), name);
    }
}
