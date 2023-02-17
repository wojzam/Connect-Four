package com.connectfour.app.model.commands;

import com.connectfour.app.model.Board;

public class PlayTurn implements Command {

    private final Board board;
    private final int chosenColumn;
    private boolean gameOver;

    public PlayTurn(Board board, int chosenColumn) {
        this.board = board;
        this.chosenColumn = chosenColumn;
    }

    @Override
    public void execute() {
        board.insertIntoColumn(chosenColumn);
        gameOver = hasGameEnded();
        if (!gameOver) {
            board.changePlayer();
        }
    }

    @Override
    public void undo() {
        board.removeTopDiskFromColumn(chosenColumn);
        if (!gameOver) {
            board.changePlayer();
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private boolean hasGameEnded() {
        return board.currentPlayerWonGame() || board.isFull();
    }
}
