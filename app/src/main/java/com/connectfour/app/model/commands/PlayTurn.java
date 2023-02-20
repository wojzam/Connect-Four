package com.connectfour.app.model.commands;

import com.connectfour.app.model.Board;

public class PlayTurn implements Command {

    private final Board board;
    private final int chosenColumn;
    private boolean gameInProgress;

    public PlayTurn(Board board, int chosenColumn) {
        this.board = board;
        this.chosenColumn = chosenColumn;
    }

    @Override
    public void execute() {
        board.insertIntoColumn(chosenColumn);
        gameInProgress = !hasGameEnded();
        if (gameInProgress) {
            board.changePlayer();
        }
    }

    @Override
    public void undo() {
        board.removeTopDiskFromColumn(chosenColumn);
        if (gameInProgress) {
            board.changePlayer();
        }
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    private boolean hasGameEnded() {
        return board.currentPlayerWonGame() || board.isFull();
    }
}
