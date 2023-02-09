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

public class GameModel implements GameModelInterface {

    private final Settings settings;
    private final Board board;
    private final CommandHistory commands;
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private ExecutorService aiTurnExecutor;

    public GameModel(Settings settings) {
        this.settings = settings;
        this.board = new Board();
        this.commands = new CommandHistory();
        this.aiTurnExecutor = Executors.newSingleThreadExecutor();
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
    }

    @Override
    public void undoTurn() {
        commands.undoLastCommand();
        if (isAIOpponentEnabled()) {
            commands.undoLastCommand();
        }
    }

    @Override
    public void columnClickAction(int columnIndex) {
        if (board.canInsertInColumn(columnIndex)) {
            playTurn(columnIndex);
            finalizeTurn();
        }
    }

    private boolean isAIOpponentEnabled() {
        return settings.getSinglePlayer();
    }

    private boolean isAIOpponentTurn() {
        return board.getCurrentPlayerDisk() == AI.AI_DISK;
    }

    private void finalizeTurn() {
        if (board.currentPlayerWonGame() || board.isFull()) {
            gameObservers.forEach(GameObserver::disableTurn);
        } else {
            board.changePlayer();
            if (isAIOpponentEnabled() && isAIOpponentTurn()) {
                aiTurn();
            }
        }
        gameObservers.forEach(GameObserver::updateGameStatus);
    }

    private void aiTurn() {
        gameObservers.forEach(GameObserver::disableTurn);
        final Handler aiTurnHandler = new Handler(Looper.getMainLooper());

        aiTurnExecutor.execute(() -> {
            final int aiColumn = AI.chooseColumn(board, settings.getDifficulty());
            if (Thread.interrupted()) {
                return;
            }
            aiTurnHandler.post(() -> {
                playTurn(aiColumn);
                gameObservers.forEach(GameObserver::enableTurn);
                finalizeTurn();
            });
        });
    }

}
