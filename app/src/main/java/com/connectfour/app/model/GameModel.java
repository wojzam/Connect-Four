package com.connectfour.app.model;

import android.os.Handler;
import android.os.Looper;

import com.connectfour.app.Settings;
import com.connectfour.app.ai.AI;
import com.connectfour.app.model.commands.CommandHistory;
import com.connectfour.app.model.commands.PlayTurn;
import com.connectfour.app.views.ViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameModel implements ModelInterface {

    private final Settings settings;
    private final Board board = new Board();
    private final CommandHistory commands = new CommandHistory();
    private final List<ViewInterface> gameObservers = new ArrayList<>();
    private final AI ai = new AI();
    private ExecutorService aiTurnExecutor;

    public GameModel(Settings settings) {
        this.settings = settings;
        aiTurnExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void addGameObserver(ViewInterface observer) {
        gameObservers.add(observer);
    }

    @Override
    public void restart() {
        board.reset();
        commands.clear();
        resetExecutor();
        handleNextTurn();
    }

    private void resetExecutor() {
        if (aiTurnExecutor != null) {
            aiTurnExecutor.shutdownNow();
        }
        aiTurnExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public boolean canUndo() {
        return !commands.isEmpty();
    }

    private boolean isAIOpponentEnabled() {
        return settings.isSinglePlayer();
    }

    private boolean isAIOpponentTurn() {
        return isAIOpponentEnabled() && board.getCurrentPlayerDisk() == settings.getAIPlayerDisk();
    }

    private boolean isNotFirstTurnAI() {
        return !(commands.isEmpty() && isAIOpponentTurn());
    }

    @Override
    public void playTurn(int chosenColumn) {
        PlayTurn playTurn = new PlayTurn(board, chosenColumn);
        if (isNotFirstTurnAI()) {
            commands.push(playTurn);
        }
        playTurn.execute();
        gameObservers.forEach(ViewInterface::update);

        if (playTurn.isGameInProgress()) {
            handleNextTurn();
        }
    }

    @Override
    public void undoTurn() {
        commands.undoLastCommand();
        if (isAIOpponentTurn()) {
            commands.undoLastCommand();
        }
        gameObservers.forEach(ViewInterface::requestPlayerMove);
    }

    private void handleNextTurn() {
        if (isAIOpponentTurn()) {
            aiTurn();
        } else {
            gameObservers.forEach(ViewInterface::requestPlayerMove);
        }
    }

    private void aiTurn() {
        final Handler aiTurnHandler = new Handler(Looper.getMainLooper());

        aiTurnExecutor.execute(() -> {
            final int aiColumn = ai.chooseColumn(board, settings.getDifficulty());
            if (Thread.interrupted()) {
                return;
            }
            aiTurnHandler.post(() -> playTurn(aiColumn));
        });
    }

    @Override
    public Board getBoard() {
        return board;
    }

}
