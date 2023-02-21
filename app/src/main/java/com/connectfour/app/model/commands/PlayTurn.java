package com.connectfour.app.model.commands;

import com.connectfour.app.model.Board;

/**
 * The {@code PlayTurn} command can be executed to play a turn in Connect Four game by
 * inserting a disk into a chosen column and changing the player if the game is still in progress.
 * It can be undone to remove the inserted disk from the board and revert the player to the one before the turn was played.
 * This command also keeps track if the game was still in progress as a result of this turn which additionally indicates
 * whether it should change the current player.
 */
public class PlayTurn implements Command {

    private final Board board;
    private final int chosenColumn;
    private boolean gameInProgress;

    /**
     * Constructs a new {@code PlayTurn} command with the given {@link Board} and chosen column.
     *
     * @param board        the game board on which to play the turn
     * @param chosenColumn the index of the column in which to insert the disk
     */
    public PlayTurn(Board board, int chosenColumn) {
        this.board = board;
        this.chosenColumn = chosenColumn;
    }

    /**
     * Inserts a disk into the board at the chosen column and changes the current player
     * if the game is still in progress.
     */
    @Override
    public void execute() {
        board.insertIntoColumn(chosenColumn);
        gameInProgress = !hasGameEnded();
        if (gameInProgress) {
            board.changePlayer();
        }
    }

    /**
     * Undoes the command by removing the top disk from the previously chosen column of the board and
     * reverting the player to the one before the turn was played.
     */
    @Override
    public void undo() {
        board.removeTopDiskFromColumn(chosenColumn);
        if (gameInProgress) {
            board.changePlayer();
        }
    }

    /**
     * @return true if the game is still in progress
     */
    public boolean isGameInProgress() {
        return gameInProgress;
    }

    /**
     * Checks whether the game ended as a result of this turn.
     *
     * @return true if the game has ended
     */
    private boolean hasGameEnded() {
        return board.currentPlayerWonGame() || board.isFull();
    }
}
