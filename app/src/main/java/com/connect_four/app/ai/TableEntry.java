package com.connect_four.app.ai;

public class TableEntry {

    private final int score;
    private final boolean gameEnded;

    public TableEntry(int score, boolean gameEnded) {
        this.score = score;
        this.gameEnded = gameEnded;
    }

    public int getScore() {
        return score;
    }

    public boolean hasGameEnd() {
        return gameEnded;
    }
}
