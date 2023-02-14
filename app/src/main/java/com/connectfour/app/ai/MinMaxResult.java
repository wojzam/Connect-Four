package com.connectfour.app.ai;

public class MinMaxResult {

    private final int column;
    private final int score;

    public MinMaxResult(int column, int score) {
        this.column = column;
        this.score = score;
    }

    public int getColumn() {
        return column;
    }

    public int getScore() {
        return score;
    }
}
