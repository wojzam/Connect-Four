package com.connectfour.app.model;

import android.os.Handler;
import android.os.Looper;

import com.connectfour.app.Settings;
import com.connectfour.app.ai.AI;
import com.connectfour.app.commands.CommandHistory;
import com.connectfour.app.commands.PlayTurn;
import com.connectfour.app.views.GameObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameModel implements GameModelInterface {

    private final Settings settings;
    private final Board board = new Board();
    private final CommandHistory commands = new CommandHistory();
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private final AI ai = new AI();
    private ExecutorService aiTurnExecutor;

    public GameModel(Settings settings) {
        this.settings = settings;
        aiTurnExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void addGameObserver(GameObserver observer) {
        gameObservers.add(observer);
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public void restart() {
        board.reset();
        commands.clear();
        resetExecutor();
        if (isAIOpponentTurn()) {
            aiTurn();
        } else {
            gameObservers.forEach(gameObserver -> gameObserver.setEnabled(true));
        }
        gameObservers.forEach(gameObserver -> gameObserver.setUndoEnabled(canUndo()));
    }

    private void resetExecutor() {
        if (aiTurnExecutor != null) {
            aiTurnExecutor.shutdownNow();
        }
        aiTurnExecutor = Executors.newSingleThreadExecutor();
    }

    private void playTurn(int chosenColumn) {
        commands.executeAndSave(new PlayTurn(board, chosenColumn));
        gameObservers.forEach(gameObserver -> gameObserver.updateColumn(chosenColumn));
        gameObservers.forEach(gameObserver -> gameObserver.setUndoEnabled(canUndo()));
    }

    @Override
    public void undoTurn() {
        commands.undoLastCommand();
        if (isAIOpponentEnabled()) {
            commands.undoLastCommand();
        }
        gameObservers.forEach(gameObserver -> gameObserver.setUndoEnabled(canUndo()));
    }

    private boolean canUndo() {
        return commands.size() > 0;
    }

    @Override
    public void columnClickAction(int columnIndex) {
        if (board.canInsertInColumn(columnIndex)) {
            playTurn(columnIndex);
            finalizeTurn();
        }
    }

    private boolean isAIOpponentEnabled() {
        return settings.isSinglePlayer();
    }

    private boolean isAIOpponentTurn() {
        return isAIOpponentEnabled() && board.getCurrentPlayerDisk() == settings.getAIPlayerDisk();
    }

    private void finalizeTurn() {
        if (board.currentPlayerWonGame() || board.isFull()) {
            gameObservers.forEach(gameObserver -> gameObserver.setEnabled(false));
        } else {
            board.changePlayer();
            if (isAIOpponentTurn()) {
                aiTurn();
            }
        }
        gameObservers.forEach(GameObserver::updateGameStatus);
    }

    private void aiTurn() {
        gameObservers.forEach(gameObserver -> gameObserver.setEnabled(false));
        final Handler aiTurnHandler = new Handler(Looper.getMainLooper());

        aiTurnExecutor.execute(() -> {
            final int aiColumn = ai.chooseColumn(board, settings.getDifficulty());
            if (Thread.interrupted()) {
                return;
            }
            aiTurnHandler.post(() -> {
                playTurn(aiColumn);
                gameObservers.forEach(gameObserver -> gameObserver.setEnabled(true));
                finalizeTurn();
            });
        });
    }

}
