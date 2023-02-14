package com.connectfour.app.ai;

import com.connectfour.app.model.Board;

import java.util.ArrayList;
import java.util.Collections;

public class RandomMovesStrategy extends MovesStrategy {

    @Override
    public ArrayList<Integer> getPossibleMoves(Board board) {
        ArrayList<Integer> possibleMoves = super.getPossibleMoves(board);
        Collections.shuffle(possibleMoves);

        return possibleMoves;
    }
}
