package com.connectfour.app.ai;

import com.connectfour.app.model.Board;

import java.util.ArrayList;

public abstract class MovesStrategy {

    ArrayList<Integer> getPossibleMoves(Board board) {
        ArrayList<Integer> possibleMoves = new ArrayList<>(board.getWidth());
        for (int i = 0; i < board.getWidth(); i++) {
            if (board.canInsertInColumn(i)) {
                possibleMoves.add(i);
            }
        }
        return possibleMoves;
    }

}
