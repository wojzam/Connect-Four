package com.connect_four.app;

import android.widget.LinearLayout;

public class Game {

    private final Board board;
    private final GameViews gameViews;
    private boolean gameOver;

    public Game(LinearLayout mainLayout) {
        this.board = new Board();
        this.gameViews = new GameViews(mainLayout, board);
        this.gameOver = false;

        gameViews.updateTextToDescribeBoardStatus();
    }

    public void restart() {
        board.resetBoard();
        gameViews.getBoardLayout().refresh();
        gameViews.getBoardLayout().columnsSetOnClickListener(view -> playerClickAction((ColumnLayout) view));
        gameOver = false;

        gameViews.updateTextToDescribeBoardStatus();
    }

    private void playerClickAction(ColumnLayout clickedColumn) {
        int column = clickedColumn.getIndex();

        if (board.insertIntoColumn(column)) {
            gameViews.getBoardLayout().refreshColumn(column);
            finalizeTurn();
            if (!gameOver) {
                aiTurn();
            }
        }
    }

    private void finalizeTurn() {
        if (board.currentPlayerWonGame()) {
            endGame();
        } else if (board.isFull()) {
            endGame();
        } else {
            board.changePlayer();
        }
        gameViews.updateTextToDescribeBoardStatus();
    }

    private void endGame() {
        gameOver = true;
        gameViews.getBoardLayout().columnsRemoveOnClickListener();
    }

    private void aiTurn() {
        int aiColumn = AI.chooseColumn(board);
        board.insertIntoColumn(aiColumn);
        gameViews.getBoardLayout().refreshColumn(aiColumn);
        finalizeTurn();
    }
}
