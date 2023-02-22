package com.connectfour.app;

import com.connectfour.app.ai.BoardEvaluator;
import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;

import org.junit.jupiter.api.Test;

import static com.connectfour.app.BoardTestData.EMPTY_BOARD;
import static com.connectfour.app.BoardTestData.FULL_BOARD;
import static com.connectfour.app.BoardTestData.THREE_IN_ROW_HORIZONTAL;
import static com.connectfour.app.ai.BoardEvaluator.SCORE_THREE_IN_ROW;
import static com.connectfour.app.ai.BoardEvaluator.SCORE_THREE_IN_ROW_OPPONENT;
import static com.connectfour.app.ai.BoardEvaluator.SCORE_TWO_IN_ROW;
import static com.connectfour.app.model.Disk.EMPTY;
import static com.connectfour.app.model.Disk.PLAYER_1;
import static com.connectfour.app.model.Disk.PLAYER_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardEvaluatorTest {

    @Test
    void shouldReturnScore_whenBoardIsEmpty() {
        BoardEvaluator evaluator = new BoardEvaluator(new Board(EMPTY_BOARD));

        assertEquals(0, evaluator.evaluateFor(PLAYER_1));
    }

    @Test
    void shouldReturnScore_whenBoardIsFull() {
        BoardEvaluator evaluator = new BoardEvaluator(new Board(FULL_BOARD));

        assertEquals(0, evaluator.evaluateFor(PLAYER_1));
    }

    @Test
    void shouldReturnScore_whenSequenceHasTwoInRow() {
        BoardEvaluator evaluator = new BoardEvaluator(new Board(EMPTY_BOARD));
        evaluator.insertIntoColumn(0);
        evaluator.insertIntoColumn(1);

        assertEquals(SCORE_TWO_IN_ROW, evaluator.evaluateFor(PLAYER_1));
    }

    @Test
    void shouldReturnScore_whenSequenceHasThreeInRow() {
        BoardEvaluator evaluator = new BoardEvaluator(new Board(THREE_IN_ROW_HORIZONTAL));

        assertEquals(SCORE_THREE_IN_ROW, evaluator.evaluateFor(PLAYER_1));
    }

    @Test
    void shouldReturnScore_whenOpponentHasThreeInRow() {
        BoardEvaluator evaluator = new BoardEvaluator(new Board(THREE_IN_ROW_HORIZONTAL));

        assertEquals(SCORE_THREE_IN_ROW_OPPONENT, evaluator.evaluateFor(PLAYER_2));
    }

    @Test
    void shouldReturnScore_whenBoardIsMoreComplex() {
        Disk[][] complexBoard = {
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {PLAYER_2, PLAYER_1, EMPTY, EMPTY, EMPTY},
                {PLAYER_2, PLAYER_1, PLAYER_1, EMPTY, EMPTY},
                {PLAYER_2, PLAYER_1, PLAYER_1, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}};
        BoardEvaluator evaluator = new BoardEvaluator(new Board(complexBoard));

        int expectedScore = 8 * SCORE_TWO_IN_ROW + 2 * SCORE_THREE_IN_ROW + 2 * SCORE_THREE_IN_ROW_OPPONENT;
        assertEquals(expectedScore, evaluator.evaluateFor(PLAYER_1));
    }
}
