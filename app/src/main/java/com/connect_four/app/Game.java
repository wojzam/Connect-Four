package com.connect_four.app;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import static com.connect_four.app.Board.PLAYER_1;
import static com.connect_four.app.Board.PLAYER_2;

public class Game {

    private final BoardLayout boardLayout;
    private final Board board;
    private final LinearLayout mainLayout;
    private final TextView text;
    private boolean gameOver;

    public Game(LinearLayout mainLayout) {
        this.board = new Board();
        this.mainLayout = mainLayout;
        this.text = new TextView(mainLayout.getContext());
        this.boardLayout = new BoardLayout(mainLayout.getContext(), board);
        this.gameOver = false;
        arrangeViews();
    }

    public void restart() {
        board.resetBoard();
        boardLayout.refresh();
        boardLayout.columnsSetOnClickListener(view -> playerClickAction((ColumnLayout) view));
        gameOver = false;

        updateText();
    }

    private void arrangeViews() {
        mainLayout.setGravity(Gravity.CENTER);
        text.setTextSize(30);
        text.setGravity(Gravity.CENTER);

        mainLayout.addView(boardLayout);
        mainLayout.addView(text);
    }

    private void playerClickAction(ColumnLayout clickedColumn) {
        int column = clickedColumn.getIndex();

        if (board.insertIntoColumn(column)) {
            boardLayout.refreshColumn(column);
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
            // TODO: This should better be inside updateText()
            text.setText(R.string.tie_info);
            text.setTextColor(ContextCompat.getColor(mainLayout.getContext(), R.color.white));
            return;
        } else {
            board.changePlayer();
        }
        updateText();
    }

    private void endGame() {
        gameOver = true;
        boardLayout.columnsRemoveOnClickListener();
    }

    private void aiTurn() {
        int aiColumn = AI.chooseColumn(board);
        board.insertIntoColumn(aiColumn);
        boardLayout.refreshColumn(aiColumn);
        finalizeTurn();
    }

    private void updateText() {
        switch (board.getCurrentPlayerID()) {
            case PLAYER_1:
                if (gameOver) {
                    text.setText(R.string.player_1_won);
                } else {
                    text.setText(R.string.player_1_turn);
                }
                text.setTextColor(ContextCompat.getColor(mainLayout.getContext(), R.color.player1));
                break;
            case PLAYER_2:
                if (gameOver) {
                    text.setText(R.string.player_2_won);
                } else {
                    text.setText(R.string.player_2_turn);
                }
                text.setTextColor(ContextCompat.getColor(mainLayout.getContext(), R.color.player2));
                break;
            default:
                text.setText("");
        }
    }
}
