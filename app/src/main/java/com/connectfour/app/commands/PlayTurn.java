package com.connectfour.app.commands;

import com.connectfour.app.model.Board;

public class PlayTurn implements Command {

    private final Board board;
    private final int chosenColumn;

    public PlayTurn(Board board, int chosenColumn) {
        this.board = board;
        this.chosenColumn = chosenColumn;
    }

    @Override
    public void execute() {
        board.insertIntoColumn(chosenColumn);
    }

    @Override
    public void undo() {
        board.removeTopDiskFromColumn(chosenColumn);
        board.changePlayer();
    }
}
