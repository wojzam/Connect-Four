package com.connectfour.app.ai;

import com.connectfour.app.model.Board;

import java.util.ArrayList;
import java.util.Collections;

public class RandomMovesStrategy extends MovesStrategy {

    @Override
    public ArrayList<Integer> getAvailableColumns(Board board) {
        ArrayList<Integer> availableColumns = super.getAvailableColumns(board);
        Collections.shuffle(availableColumns);

        return availableColumns;
    }
}
