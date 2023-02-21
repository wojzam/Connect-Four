package com.connectfour.app.ai;

/**
 * A class that represents the result of the MinMax algorithm.
 * It contains a chosen column and its corresponding score.
 *
 * @see AI
 */
public class MinMaxResult {

    private final int column;
    private final int score;

    /**
     * Constructs a new MinMaxResult with the given column and score.
     *
     * @param column the chosen column
     * @param score  the corresponding score
     */
    public MinMaxResult(int column, int score) {
        this.column = column;
        this.score = score;
    }

    /**
     * @return the chosen column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return the corresponding score
     */
    public int getScore() {
        return score;
    }
}
