package com.connectfour.app.ai;

import com.connectfour.app.model.Board;

import java.util.ArrayList;

public class OptimalMovesStrategy extends MovesStrategy {

    private static void sortMoves(ArrayList<Integer> possibleMoves, int centerColumn) {
        possibleMoves.sort((a, b) -> {
            int diffA = Math.abs(a - centerColumn);
            int diffB = Math.abs(b - centerColumn);
            return Integer.compare(diffA, diffB);
        });
    }

    @Override
    public ArrayList<Integer> getPossibleMoves(Board board) {
        ArrayList<Integer> possibleMoves = super.getPossibleMoves(board);
        sortMoves(possibleMoves, board.getWidth() / 2);

        return possibleMoves;
    }
}
