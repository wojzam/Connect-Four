package com.connectfour.app.model;

import android.os.Handler;
import android.os.Looper;

import com.connectfour.app.Settings;
import com.connectfour.app.ai.AI;
import com.connectfour.app.controller.ControllerInterface;
import com.connectfour.app.model.commands.CommandHistory;
import com.connectfour.app.model.commands.PlayTurn;
import com.connectfour.app.views.ViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The {@code GameModel} class represents the Model component of the MVC design pattern for the Connect Four game
 * and is responsible for managing the game's logic.
 * It maintains a {@link Board} and keeps a history of the turns played, allowing them to be undone.
 * This class can be the subject of {@link ViewInterface} observation, enabling it to notify the view when updates are needed.
 * <p>
 * Additionally, the class includes an AI player represented by the {@link AI} object.
 *
 * @see ViewInterface
 * @see ControllerInterface
 * @see Board
 * @see AI
 */
public class GameModel implements ModelInterface {

    private final Settings settings;
    private final Board board = new Board();
    private final CommandHistory commands = new CommandHistory();
    private final List<ViewInterface> gameObservers = new ArrayList<>();
    private final AI ai = new AI();

    /**
     * The {@code ExecutorService} object used to execute the AI player's turns asynchronously.
     */
    private ExecutorService aiTurnExecutor;

    /**
     * Constructs a new {@code GameModel} with the given {@link Settings}.
     *
     * @param settings the settings for the game
     */
    public GameModel(Settings settings) {
        this.settings = settings;
        aiTurnExecutor = Executors.newSingleThreadExecutor();
    }

    /**
     * {@inheritDoc}
     *
     * @param observer {@inheritDoc}
     */
    @Override
    public void addGameObserver(ViewInterface observer) {
        gameObservers.add(observer);
    }

    /**
     * Resets the game state to the initial state and starts a new game.
     */
    @Override
    public void restart() {
        board.reset();
        commands.clear();
        resetExecutor();
        handleNextTurn();
    }

    /**
     * Resets the {@link #aiTurnExecutor}.
     */
    private void resetExecutor() {
        if (aiTurnExecutor != null) {
            aiTurnExecutor.shutdownNow();
        }
        aiTurnExecutor = Executors.newSingleThreadExecutor();
    }

    /**
     * @return {@inheritDoc}
     */
    @Override
    public boolean canUndo() {
        return !commands.isEmpty();
    }

    /**
     * @return true if the AI opponent is enabled
     */
    private boolean isAIOpponentEnabled() {
        return settings.isSinglePlayer();
    }

    /**
     * @return true if it is currently the AI opponent's turn
     */
    private boolean isAIOpponentTurn() {
        return isAIOpponentEnabled() && board.getCurrentPlayerDisk() == settings.getAIPlayerDisk();
    }

    /**
     * @return true if it is not the first turn in the game which also belongs to the AI opponent
     */
    private boolean isNotFirstTurnAI() {
        return !(commands.isEmpty() && isAIOpponentTurn());
    }

    /**
     * This method executes the {@link PlayTurn} command, which drops a disk in the specified column
     * and changes the current player if the game is still in progress. It then saves the played turn
     * to the history and updates the view. However, if it's the first turn of the game, which also belongs
     * to the AI opponent, it doesn't save the turn, as undoing only the AI player's turn wouldn't make sense.
     * If the game hasn't finished yet, the method calls {@link #handleNextTurn()}.
     *
     * @param chosenColumn the column in which to play the disk
     * @see PlayTurn
     */
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

    /**
     * Undo the last turn that was played and if now it is AI player turn, that turn is also undone.
     * After the undo action is complete, the method notifies the view to enable the player to make its move.
     */
    @Override
    public void undoTurn() {
        commands.undoLastCommand();
        if (isAIOpponentTurn()) {
            commands.undoLastCommand();
        }
        gameObservers.forEach(ViewInterface::requestPlayerMove);
    }

    /**
     * Handles the next turn, either by requesting a player move or executing an AI turn.
     */
    private void handleNextTurn() {
        if (isAIOpponentTurn()) {
            aiTurn();
        } else {
            gameObservers.forEach(ViewInterface::requestPlayerMove);
        }
    }

    /**
     * Executes an AI turn on a separate thread.
     */
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

    /**
     * @return {@inheritDoc}
     */
    @Override
    public Board getBoard() {
        return board;
    }

}
