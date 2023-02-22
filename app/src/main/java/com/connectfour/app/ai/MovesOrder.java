package com.connectfour.app.ai;

import com.connectfour.app.model.Board;

import java.util.Collections;
import java.util.List;

/**
 * The {@code MovesOrder} class provides methods for ordering the possible moves in Connect Four game.
 * It chooses more optimal order of available columns at higher levels of MinMax game tree, when it is more crucial
 * and random order at lower levels to add randomness to behaviour of the {@link AI} player.
 *
 * @see AI
 */
public class MovesOrder {

    /**
     * The random order threshold value was chosen based on experimental results to improve performance.
     */
    public static final int RANDOM_ORDER_THRESHOLD = 3;

    /**
     * Sorts the list of columns based on their distance from the center column of the game board.
     * Moves made on the center column are generally considered better,
     * which helps the MinMax algorithm to find the best move faster.
     *
     * @param columns       the list of columns to sort
     * @param centralColumn the central column of the board
     */
    private static void sortByDistanceToCenter(List<Integer> columns, int centralColumn) {
        columns.sort((a, b) -> {
            int diffA = Math.abs(a - centralColumn);
            int diffB = Math.abs(b - centralColumn);
            return Integer.compare(diffA, diffB);
        });
    }

    /**
     * Returns the available columns on the {@link Board} where a disk can be inserted
     * in the most suitable order based on the current depth level.
     *
     * @param board the current game board
     * @param depth the current depth of the Minimax algorithm
     * @return a list of available columns
     * @see #RANDOM_ORDER_THRESHOLD
     */
    public static List<Integer> getAvailableColumns(Board board, int depth) {
        List<Integer> availableColumns = board.getAvailableColumns();
        if (depth <= RANDOM_ORDER_THRESHOLD) {
            Collections.shuffle(availableColumns);
        } else {
            sortByDistanceToCenter(availableColumns, board.getWidth() / 2);
        }
        return availableColumns;
    }

}
