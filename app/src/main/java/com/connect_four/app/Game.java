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

    public Game(LinearLayout mainLayout) {
        this.board = new Board();
        this.mainLayout = mainLayout;
        this.text = new TextView(mainLayout.getContext());
        this.boardLayout = new BoardLayout(mainLayout.getContext(), board);
        arrangeViews();
    }

    public void restart() {
        board.resetBoard();
        boardLayout.refresh();
        boardLayout.columnsSetOnClickListener(view -> playerClickAction((ColumnLayout) view));

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
            if (!hasGameEnded()) {
                aiTurn();
            }
        }
    }

    private boolean hasGameEnded() {
        if (board.wonGame() || board.isFull()) {
            text.setText("Koniec gry");
            boardLayout.columnsRemoveOnClickListener();
            return true;
        }
        board.changePlayer();
        updateText();

        return false;
    }

    private void aiTurn() {
        int aiColumn = AI.chooseColumn(board);
        board.insertIntoColumn(aiColumn);
        boardLayout.refreshColumn(aiColumn);
        hasGameEnded();
    }

    private void updateText() {
        switch (board.getCurrentPlayerID()) {
            case PLAYER_1:
                text.setText("Czerwony");
                text.setTextColor(ContextCompat.getColor(mainLayout.getContext(), R.color.player1));
                break;
            case PLAYER_2:
                text.setText("Żółty");
                text.setTextColor(ContextCompat.getColor(mainLayout.getContext(), R.color.player2));
                break;
            default:
                text.setText("");
        }
    }
}
