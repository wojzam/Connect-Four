package com.connectfour.app.ai;

public class StrategyFactory {

    private static final MovesStrategy randomStrategy = new RandomMovesStrategy();
    private static final MovesStrategy optimalStrategy = new OptimalMovesStrategy();

    public static MovesStrategy getStrategy(int depth) {
        if (depth < 7) {
            return randomStrategy;
        }
        return optimalStrategy;
    }
}
