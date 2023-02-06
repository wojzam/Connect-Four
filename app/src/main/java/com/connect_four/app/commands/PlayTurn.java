package com.connect_four.app.commands;

import com.connect_four.app.Board;
import com.connect_four.app.views.GameViews;

public class PlayTurn implements Command {

    private final Board board;
    private final GameViews gameViews;
    private final int chosenColumn;

    public PlayTurn(Board board, GameViews gameViews, int chosenColumn) {
        this.board = board;
        this.gameViews = gameViews;
        this.chosenColumn = chosenColumn;
    }

    @Override
    public void execute() {
        board.insertIntoColumn(chosenColumn);
        gameViews.getBoardLayout().refreshColumn(chosenColumn);
    }

    @Override
    public void undo() {
        board.removeTopDiskFromColumn(chosenColumn);
        gameViews.getBoardLayout().refreshColumn(chosenColumn);
        board.changePlayer();
    }
}
