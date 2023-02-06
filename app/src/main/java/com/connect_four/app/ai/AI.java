package com.connect_four.app.ai;

import com.connect_four.app.Board;
import com.connect_four.app.Disk;

import java.util.ArrayList;
import java.util.Collections;

import static com.connect_four.app.Disk.PLAYER_1;
import static com.connect_four.app.Disk.PLAYER_2;

public class AI {
    private static final int WINING_MOVE_SCORE = 1000000000;
    private static final int LOSING_MOVE_SCORE = -1000000000;
    private static final int TIE_MOVE_SCORE = 0;

    private static final Disk AI_DISK = PLAYER_2;
    private static final Disk HUMAN_DISK = PLAYER_1;

    public static int chooseColumn(Board board, int depth) {
        assert depth > 0 : "Depth should be greater than zero";
        Board boardCopy = new Board(board);
        boardCopy.changePlayer();

        return minMax(boardCopy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getColumn();
    }

    private static ArrayList<Integer> getPossibleMoves(Board board) {
        ArrayList<Integer> possibleMoves = new ArrayList<>();

        for (int i = 0; i < board.getWidth(); i++) {
            if (!board.isColumnFull(i)) {
                possibleMoves.add(i);
            }
        }
        Collections.shuffle(possibleMoves);

        return possibleMoves;
    }

    private static MinMaxResult minMax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (board.currentPlayerWonGame()) {
            if (maximizingPlayer) {
                return new MinMaxResult(-1, LOSING_MOVE_SCORE - depth);
            }
            return new MinMaxResult(-1, WINING_MOVE_SCORE + depth);
        }
        if (board.isFull()) {
            return new MinMaxResult(-1, TIE_MOVE_SCORE);
        }
        if (depth == 0) {
            return new MinMaxResult(-1, BoardEvaluator.evaluate(board, AI_DISK));
        }

        ArrayList<Integer> possibleMoves = getPossibleMoves(board);
        int score;
        int column = possibleMoves.get(0);
        board.changePlayer();

        if (maximizingPlayer) {
            score = Integer.MIN_VALUE;
            for (int col : possibleMoves) {
                Board boardCopy = new Board(board);
                boardCopy.insertIntoColumn(col, AI_DISK);
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
                boardCopy.insertIntoColumn(col, HUMAN_DISK);
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
