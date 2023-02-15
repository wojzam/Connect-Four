package com.connectfour.app.ai;

import com.connectfour.app.model.Board;

import java.util.ArrayList;

public class OptimalMovesStrategy extends MovesStrategy {

    private static void sortColumns(ArrayList<Integer> availableColumns, int centerColumn) {
        availableColumns.sort((a, b) -> {
            int diffA = Math.abs(a - centerColumn);
            int diffB = Math.abs(b - centerColumn);
            return Integer.compare(diffA, diffB);
        });
    }

    @Override
    public ArrayList<Integer> getAvailableColumns(Board board) {
        ArrayList<Integer> availableColumns = super.getAvailableColumns(board);
        sortColumns(availableColumns, board.getWidth() / 2);

        return availableColumns;
    }
}
