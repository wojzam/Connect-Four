package com.connect_four.app;

import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;

import com.connect_four.app.ai.AI;
import com.connect_four.app.commands.Command;
import com.connect_four.app.commands.CommandHistory;
import com.connect_four.app.commands.PlayTurn;
import com.connect_four.app.views.ColumnLayout;
import com.connect_four.app.views.GameViews;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private final Settings settings;
    private final Board board;
    private final GameViews gameViews;
    private final CommandHistory commands;
    private ExecutorService aiTurnExecutor;

    public Game(LinearLayout mainLayout, Settings settings) {
        this.settings = settings;
        this.board = new Board();
        this.gameViews = new GameViews(mainLayout, board);
        this.commands = new CommandHistory();
        this.aiTurnExecutor = Executors.newSingleThreadExecutor();

        gameViews.getUndoButton().setOnClickListener(view -> undoTurn());
        gameViews.updateTextToDescribeBoardStatus();
    }

    public void restart() {
        board.resetBoard();
        commands.clear();
        gameViews.getBoardLayout().refresh();
        gameViews.getBoardLayout().columnsSetOnClickListener(view -> columnClickAction((ColumnLayout) view));
        gameViews.getUndoButton().setEnabled(true);

        resetExecutor();
        gameViews.updateTextToDescribeBoardStatus();
    }

    private void resetExecutor() {
        if (aiTurnExecutor != null) {
            aiTurnExecutor.shutdownNow();
        }
        aiTurnExecutor = Executors.newSingleThreadExecutor();
    }

    private void playTurn(int chosenColumn) {
        Command playTurn = new PlayTurn(board, gameViews, chosenColumn);
        playTurn.execute();
        commands.push(playTurn);
    }

    private void undoTurn() {
        commands.undoLastCommand();
        if (isAIOpponentEnabled()) {
            commands.undoLastCommand();
        }
        gameViews.updateTextToDescribeBoardStatus();
    }

    private void columnClickAction(ColumnLayout clickedColumn) {
        int column = clickedColumn.getIndex();

        if (board.canInsertInColumn(column)) {
            playTurn(column);
            finalizeTurn();
        }
    }

    private boolean isAIOpponentEnabled() {
        return settings.getSinglePlayer();
    }

    private boolean isAIOpponentTurn() {
        return board.getCurrentPlayerDisk() == AI.AI_DISK;
    }

    private void endGame() {
        gameViews.getBoardLayout().columnsRemoveOnClickListener();
        gameViews.getUndoButton().setEnabled(false);
    }

    private void finalizeTurn() {
        if (board.currentPlayerWonGame() || board.isFull()) {
            endGame();
        } else {
            board.changePlayer();
            if (isAIOpponentEnabled() && isAIOpponentTurn()) {
                aiTurn();
            }
        }
        gameViews.updateTextToDescribeBoardStatus();
    }

    private void aiTurn() {
        gameViews.getBoardLayout().columnsRemoveOnClickListener();
        gameViews.getUndoButton().setEnabled(false);
        final Handler aiTurnHandler = new Handler(Looper.getMainLooper());

        aiTurnExecutor.execute(() -> {
            final int aiColumn = AI.chooseColumn(board, settings.getDifficulty());
            if (Thread.interrupted()) {
                return;
            }
            aiTurnHandler.post(() -> {
                playTurn(aiColumn);
                gameViews.getBoardLayout().columnsSetOnClickListener(view -> columnClickAction((ColumnLayout) view));
                gameViews.getUndoButton().setEnabled(true);
                finalizeTurn();
            });
        });
    }

}
