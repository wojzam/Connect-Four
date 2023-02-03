package com.connect_four.app;

import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;

import com.connect_four.app.ai.AI;
import com.connect_four.app.views.ColumnLayout;
import com.connect_four.app.views.GameViews;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private final Settings settings;
    private final Board board;
    private final GameViews gameViews;
    private ExecutorService aiTurnExecutor;
    private boolean gameOver;

    public Game(LinearLayout mainLayout, Settings settings) {
        this.settings = settings;
        this.board = new Board();
        this.gameViews = new GameViews(mainLayout, board);
        this.aiTurnExecutor = Executors.newSingleThreadExecutor();
        this.gameOver = false;

        gameViews.updateTextToDescribeBoardStatus();
    }

    public void restart() {
        board.resetBoard();
        gameViews.getBoardLayout().refresh();
        gameViews.getBoardLayout().columnsSetOnClickListener(view -> playerClickAction((ColumnLayout) view));
        gameOver = false;

        resetExecutor();
        gameViews.updateTextToDescribeBoardStatus();
    }

    private void resetExecutor() {
        if (aiTurnExecutor != null) {
            aiTurnExecutor.shutdownNow();
        }
        aiTurnExecutor = Executors.newSingleThreadExecutor();
    }

    private void playerClickAction(ColumnLayout clickedColumn) {
        int column = clickedColumn.getIndex();

        if (board.insertIntoColumn(column)) {
            gameViews.getBoardLayout().refreshColumn(column);
            finalizeTurn();
            if (shouldAIMakeItsTurn()) {
                aiTurn();
            }
        }
    }

    private boolean shouldAIMakeItsTurn() {
        return settings.getSinglePlayer() && !gameOver;
    }

    private void finalizeTurn() {
        if (board.currentPlayerWonGame()) {
            endGame();
        } else if (board.isFull()) {
            endGame();
        } else {
            board.changePlayer();
        }
        gameViews.updateTextToDescribeBoardStatus();
    }

    private void endGame() {
        gameOver = true;
        gameViews.getBoardLayout().columnsRemoveOnClickListener();
    }

    private void aiTurn() {
        gameViews.getBoardLayout().columnsRemoveOnClickListener();
        final Handler aiTurnHandler = new Handler(Looper.getMainLooper());

        aiTurnExecutor.execute(() -> {
            final int aiColumn = AI.chooseColumn(board, settings.getDifficulty());
            if (Thread.interrupted()) {
                return;
            }
            aiTurnHandler.post(() -> {
                board.insertIntoColumn(aiColumn);
                gameViews.getBoardLayout().refreshColumn(aiColumn);
                gameViews.getBoardLayout().columnsSetOnClickListener(view -> playerClickAction((ColumnLayout) view));
                finalizeTurn();
            });
        });
    }
}
