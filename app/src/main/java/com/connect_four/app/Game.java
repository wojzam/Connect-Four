package com.connect_four.app;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import static com.connect_four.app.Board.PLAYER_1;
import static com.connect_four.app.Board.PLAYER_2;

public class Game {

    private final BoardView boardView;
    private final Board board;
    private final LinearLayout mainLayout;
    private final TextView text;

    public Game(LinearLayout mainLayout) {
        this.board = new Board();
        this.mainLayout = mainLayout;
        this.text = new TextView(mainLayout.getContext());
        this.boardView = new BoardView(mainLayout.getContext(), board);
        arrangeViews();
    }

    public void restart() {
        board.resetBoard();
        boardView.refresh();

        addOnClickListeners();

        updateText();
    }

    private void arrangeViews() {
        mainLayout.setGravity(Gravity.CENTER);
        text.setTextSize(30);
        text.setGravity(Gravity.CENTER);

        mainLayout.addView(boardView);
        mainLayout.addView(text);
    }

    private void addOnClickListeners() {
        ArrayList<ColumnLayout> columnLayouts = boardView.getColumns();
        for (int i = 0; i < columnLayouts.size(); i++) {
            int column = i;
            columnLayouts.get(i).setOnClickListener(view -> {
                if (board.insertIntoColumn(column)) {
                    boardView.refreshColumn(column);

                    if (!hasGameEnded()) {
                        aiTurn();
                    }
                }
            });
        }
    }

    private boolean hasGameEnded() {
        if (board.wonGame() || board.isFull()) {
            text.setText("Koniec gry");
            endGame();
            return true;
        }
        board.changePlayer();
        updateText();

        return false;
    }

    private void aiTurn() {
        int aiColumn = AI.chooseColumn(board);
        board.insertIntoColumn(aiColumn);
        boardView.refreshColumn(aiColumn);
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

    private void endGame() {
        for (ColumnLayout columnLayout : boardView.getColumns()) {
            columnLayout.setOnClickListener(null);
        }
    }
}
