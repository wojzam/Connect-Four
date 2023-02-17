package com.connectfour.app.ai;

import com.connectfour.app.model.Board;

import java.util.Collections;
import java.util.List;

public class MovesOrder {

    private static final int RANDOM_ORDER_THRESHOLD = 3;

    private static void sortByDistanceToCenter(List<Integer> columns, int centralColumn) {
        columns.sort((a, b) -> {
            int diffA = Math.abs(a - centralColumn);
            int diffB = Math.abs(b - centralColumn);
            return Integer.compare(diffA, diffB);
        });
    }

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
