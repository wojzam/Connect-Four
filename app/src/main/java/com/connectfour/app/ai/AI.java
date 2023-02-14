package com.connectfour.app.ai;

import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AI {

    private static final int WINING_MOVE_SCORE = 1000000000;
    private static final int LOSING_MOVE_SCORE = -1000000000;
    private static final int TIE_MOVE_SCORE = 0;
    private final HashMap<Integer, MinMaxResult> transpositionTable = new HashMap<>();
    private Disk aiDisk;

    private static ArrayList<Integer> getPossibleMoves(Board board) {
        ArrayList<Integer> possibleMoves = new ArrayList<>();

        for (int i = 0; i < board.getWidth(); i++) {
            if (board.canInsertInColumn(i)) {
                possibleMoves.add(i);
            }
        }
        Collections.shuffle(possibleMoves);

        return possibleMoves;
    }

    public int chooseColumn(Board board, int depth) {
        assert depth > 0 : "Depth should be greater than zero";
        aiDisk = board.getCurrentPlayerDisk();
        Board boardCopy = new Board(board);
        boardCopy.changePlayer();
        transpositionTable.clear();

        return lookupOrExecuteMinMax(boardCopy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getColumn();
    }

    private MinMaxResult lookupOrExecuteMinMax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        int boardHash = board.hashCode();
        if (transpositionTable.containsKey(boardHash)) {
            return transpositionTable.get(boardHash);
        }
        MinMaxResult result = minMax(board, depth, alpha, beta, maximizingPlayer);
        transpositionTable.put(boardHash, result);
        transpositionTable.put(board.hashCodeFlippedHorizontally(), result);

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
            int score = BoardEvaluator.evaluate(board, aiDisk);
            return new MinMaxResult(-1, score);
        }

        board.changePlayer();
        ArrayList<Integer> possibleMoves = getPossibleMoves(board);
        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int column = possibleMoves.get(0);

        for (int col : possibleMoves) {
            board.insertIntoColumn(col);
            int score = lookupOrExecuteMinMax(board, depth - 1, alpha, beta, !maximizingPlayer).getScore();
            board.removeTopDiskFromColumn(col);

            if ((maximizingPlayer && score > bestScore) || (!maximizingPlayer && score < bestScore)) {
                bestScore = score;
                column = col;
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
        return new MinMaxResult(column, bestScore);
    }
}
