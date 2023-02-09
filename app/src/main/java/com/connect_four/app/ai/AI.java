package com.connect_four.app.ai;

import com.connect_four.app.model.Board;
import com.connect_four.app.model.Disk;

import java.util.ArrayList;
import java.util.Collections;

public class AI {

    private static final int WINING_MOVE_SCORE = 1000000000;
    private static final int LOSING_MOVE_SCORE = -1000000000;
    private static final int TIE_MOVE_SCORE = 0;
    private final Disk aiDisk;
    private final Disk humanDisk;
    private final TranspositionTable table;

    public AI(Disk aiDisk, Disk humanDisk) {
        this.aiDisk = aiDisk;
        this.humanDisk = humanDisk;
        table = new TranspositionTable();
    }

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

    public Disk getAiDisk() {
        return aiDisk;
    }

    public int chooseColumn(Board board, int depth) {
        assert depth > 0 : "Depth should be greater than zero";
        Board boardCopy = new Board(board);
        boardCopy.changePlayer();

        return minMax(boardCopy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getColumn();
    }

    private MinMaxResult minMax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        int boardHash = board.hashCode();
        if (table.containsKey(boardHash)) {
            TableEntry entry = table.get(boardHash);
            if (entry.hasGameEnd() || depth == 0) {
                int score = maximizingPlayer ? entry.getScore() - depth : entry.getScore() + depth;
                return new MinMaxResult(-1, score);
            }
        } else {
            if (board.currentPlayerWonGame()) {
                int score = maximizingPlayer ? LOSING_MOVE_SCORE : WINING_MOVE_SCORE;
                table.put(boardHash, new TableEntry(score, true));

                score = maximizingPlayer ? score - depth : score + depth;
                return new MinMaxResult(-1, score);
            }
            if (board.isFull()) {
                table.put(boardHash, new TableEntry(TIE_MOVE_SCORE, true));
                return new MinMaxResult(-1, TIE_MOVE_SCORE);
            }
            if (depth == 0) {
                int score = BoardEvaluator.evaluate(board, aiDisk);
                table.put(boardHash, new TableEntry(score, false));
                return new MinMaxResult(-1, score);
            }
        }

        ArrayList<Integer> possibleMoves = getPossibleMoves(board);
        int score;
        int column = possibleMoves.get(0);
        board.changePlayer();

        if (maximizingPlayer) {
            score = Integer.MIN_VALUE;
            for (int col : possibleMoves) {
                Board boardCopy = new Board(board);
                boardCopy.insertIntoColumn(col, aiDisk);
                int new_score = minMax(boardCopy, depth - 1, alpha, beta, false).getScore();
                if (new_score > score) {
                    score = new_score;
                    column = col;
                }

                alpha = Math.max(alpha, score);
                if (alpha >= beta) {
                    break;
                }
            }

        } else {
            score = Integer.MAX_VALUE;
            for (int col : possibleMoves) {
                Board boardCopy = new Board(board);
                boardCopy.insertIntoColumn(col, humanDisk);
                int new_score = minMax(boardCopy, depth - 1, alpha, beta, true).getScore();
                if (new_score < score) {
                    score = new_score;
                    column = col;
                }

                alpha = Math.min(alpha, score);
                if (alpha >= beta) {
                    break;
                }
            }
        }

        return new MinMaxResult(column, score);
    }
}
