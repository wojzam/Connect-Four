package com.connectfour.app.ai;

public class StrategyFactory {

    private static final MovesStrategy randomStrategy = new RandomMovesStrategy();
    private static final MovesStrategy optimalStrategy = new OptimalMovesStrategy();
    private static final int RANDOM_STRATEGY_THRESHOLD = 3;

    public static MovesStrategy getStrategy(int depth) {
        if (depth <= RANDOM_STRATEGY_THRESHOLD) {
            return randomStrategy;
        }
        return optimalStrategy;
    }
}
