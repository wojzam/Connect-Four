package com.connectfour.app.ai;

import com.connectfour.app.model.Board;
import com.connectfour.app.model.BoardHash;
import com.connectfour.app.model.Disk;

import java.util.HashMap;
import java.util.List;

/**
 * Represents the {@code AI} opponent in the Connect Four game.
 * It uses the MinMax algorithm with alpha-beta pruning and transposition table to make its moves.
 */
public class AI {
    private static final int WINING_MOVE_SCORE = 1000000000;
    private static final int LOSING_MOVE_SCORE = -1000000000;
    private static final int TIE_MOVE_SCORE = 0;

    /**
     * A hash map used to store previously calculated {@link MinMaxResult} in order to avoid recalculating them.
     * The keys are {@link BoardHash} objects that represent the state of the game {@link Board}, and the values are {@code MinMaxResult}
     * objects that contain the score of the best move and the column where the disk should be placed.
     */
    private final HashMap<BoardHash, MinMaxResult> transpositionTable = new HashMap<>();

    /**
     * {@link Disk} of the {@link AI} player, which is used to evaluate {@link Board} state.
     */
    private Disk aiDisk;

    /**
     * Chooses the best column to play using the Minimax algorithm with alpha-beta pruning and transposition table,
     * which is implemented in the {@link #lookupOrExecuteMinMax}.
     * The method returns the column number with the highest score indicating the best move.
     *
     * @param board the game board
     * @param depth the depth of the MinMax algorithm search
     * @return the chosen column number
     */
    public int chooseColumn(Board board, int depth) {
        assert depth > 0 : "Depth should be greater than zero";
        aiDisk = board.getCurrentPlayerDisk();
        transpositionTable.clear();
        Board boardCopy = new BoardEvaluator(board);
        boardCopy.changePlayer();

        return lookupOrExecuteMinMax(boardCopy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getColumn();
    }

    /**
     * Looks up the transposition table to see if the given game state is already present. If it is present,
     * the method returns the corresponding {@link MinMaxResult}. Otherwise, it executes the {@link #minMax} algorithm
     * and returns the resulting {@code MinMaxResult}. The result is also added to the transposition table.
     *
     * @param board            the game board
     * @param depth            the depth of the MinMax algorithm search
     * @param alpha            the alpha value for alpha-beta pruning
     * @param beta             the beta value for alpha-beta pruning
     * @param maximizingPlayer whether the current player is trying to maximize their score or not
     * @return the MinMaxResult for the given game state
     * @see #transpositionTable
     */
    private MinMaxResult lookupOrExecuteMinMax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        BoardHash boardHash = board.getBoardHash();
        if (transpositionTable.containsKey(boardHash)) {
            return transpositionTable.get(boardHash);
        }
        MinMaxResult result = minMax(board, depth, alpha, beta, maximizingPlayer);
        transpositionTable.put(boardHash, result);

        return result;
    }

    /**
     * Runs the MinMax algorithm on the given board and returns {@link MinMaxResult}.
     * At each node the possible moves order is determined by the {@link MovesOrder} class.
     * When the algorithm reaches the bottom of the game tree, the current state of the board is evaluated using
     * the {@link BoardEvaluator} class.
     * The evaluation result is used to assign a score to the game state,
     * which is then propagated up the tree during the MinMax search.
     * If the game has ended during the exploration, the algorithm assigns one of the following scores:
     * {@link #WINING_MOVE_SCORE}, if AI player has won the game
     * {@link #LOSING_MOVE_SCORE}, if AI player has lost the game or
     * {@link #TIE_MOVE_SCORE}, if the game ended in a tie
     *
     * @param board            the game board
     * @param depth            the depth of the MinMax algorithm search
     * @param alpha            the alpha value for alpha-beta pruning
     * @param beta             the beta value for alpha-beta pruning
     * @param maximizingPlayer whether the current player is trying to maximize their score or not
     * @return the MinMaxResult for the given game state
     * @see MovesOrder
     * @see BoardEvaluator
     */
    private MinMaxResult minMax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (board.currentPlayerWonGame()) {
            int score = maximizingPlayer ? LOSING_MOVE_SCORE - depth : WINING_MOVE_SCORE + depth;
            return new MinMaxResult(-1, score);
        }
        if (board.isFull()) {
            return new MinMaxResult(-1, TIE_MOVE_SCORE);
        }
        if (depth == 0) {
            int score = ((BoardEvaluator) board).evaluateFor(aiDisk);
            return new MinMaxResult(-1, score);
        }

        board.changePlayer();
        List<Integer> availableColumns = MovesOrder.getAvailableColumns(board, depth);
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
