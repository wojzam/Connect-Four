package com.connectfour.app.ai;

import com.connectfour.app.model.Board;
import com.connectfour.app.model.BoardHash;
import com.connectfour.app.model.Disk;

import java.util.ArrayList;
import java.util.HashMap;

public class AI {

    private static final int WINING_MOVE_SCORE = 1000000000;
    private static final int LOSING_MOVE_SCORE = -1000000000;
    private static final int TIE_MOVE_SCORE = 0;
    private final HashMap<BoardHash, MinMaxResult> transpositionTable = new HashMap<>();
    private Disk aiDisk;

    private ArrayList<Integer> getAvailableColumns(Board board, int depth) {
        return StrategyFactory.getStrategy(depth).getAvailableColumns(board);
    }

    public int chooseColumn(Board board, int depth) {
        assert depth > 0 : "Depth should be greater than zero";
        aiDisk = board.getCurrentPlayerDisk();
        transpositionTable.clear();
        Board boardCopy = new BoardEvaluator(board);
        boardCopy.changePlayer();

        return lookupOrExecuteMinMax(boardCopy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getColumn();
    }

    private MinMaxResult lookupOrExecuteMinMax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        BoardHash boardHash = board.getBoardHash();
        if (transpositionTable.containsKey(boardHash)) {
            return transpositionTable.get(boardHash);
        }
        MinMaxResult result = minMax(board, depth, alpha, beta, maximizingPlayer);
        transpositionTable.put(boardHash, result);

        return result;
    }

    private MinMaxResult minMax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (board.currentPlayerWonGame()) {
            int score = maximizingPlayer ? LOSING_MOVE_SCORE - depth : WINING_MOVE_SCORE + depth;
            return new MinMaxResult(-1, score);
        }
        if (board.isFull()) {
            return new MinMaxResult(-1, TIE_MOVE_SCORE);
        }
        if (depth == 0) {
            int score = ((BoardEvaluator) board).evaluate(aiDisk);
            return new MinMaxResult(-1, score);
        }

        board.changePlayer();
        ArrayList<Integer> availableColumns = getAvailableColumns(board, depth);
        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestColumn = -1;

        for (int column : availableColumns) {
            board.insertIntoColumn(column);
            int score = lookupOrExecuteMinMax(board, depth - 1, alpha, beta, !maximizingPlayer).getScore();
            board.removeTopDiskFromColumn(column);

            if ((maximizingPlayer && score > bestScore) || (!maximizingPlayer && score < bestScore)) {
                bestScore = score;
                bestColumn = column;
            }

            if (maximizingPlayer) {
                alpha = Math.max(alpha, bestScore);
            } else {
                beta = Math.min(beta, bestScore);
            }

            if (alpha >= beta) {
                break;
            }
        }

        board.changePlayer();
        return new MinMaxResult(bestColumn, bestScore);
    }
}
