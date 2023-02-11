package com.connect_four.app.model;

import android.os.Handler;
import android.os.Looper;

import com.connect_four.app.Settings;
import com.connect_four.app.ai.AI;
import com.connect_four.app.commands.Command;
import com.connect_four.app.commands.CommandHistory;
import com.connect_four.app.commands.PlayTurn;
import com.connect_four.app.views.GameObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.connect_four.app.model.Disk.PLAYER_2;

public class GameModel implements GameModelInterface {

    private final Settings settings;
    private final Board board = new Board();
    private final CommandHistory commands = new CommandHistory();
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private final AI ai;
    private ExecutorService aiTurnExecutor;

    public GameModel(Settings settings) {
        this.settings = settings;
        aiTurnExecutor = Executors.newSingleThreadExecutor();
        ai = new AI(PLAYER_2);
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
        gameObservers.forEach(gameObserver -> gameObserver.setEnabled(true));
        gameObservers.forEach(gameObserver -> gameObserver.setUndoEnabled(canUndo()));
    }

    private void resetExecutor() {
        if (aiTurnExecutor != null) {
            aiTurnExecutor.shutdownNow();
        }
        aiTurnExecutor = Executors.newSingleThreadExecutor();
    }

    private void playTurn(int chosenColumn) {
        Command playTurn = new PlayTurn(board, chosenColumn);
        playTurn.execute();
        commands.push(playTurn);
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
        return board.getCurrentPlayerDisk() == ai.getAiDisk();
    }

    private void finalizeTurn() {
        if (board.currentPlayerWonGame() || board.isFull()) {
            gameObservers.forEach(gameObserver -> gameObserver.setEnabled(false));
        } else {
            board.changePlayer();
            if (isAIOpponentEnabled() && isAIOpponentTurn()) {
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
