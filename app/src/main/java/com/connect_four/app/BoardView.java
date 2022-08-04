package com.connect_four.app;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import static com.connect_four.app.Board.PLAYER_1;
import static com.connect_four.app.Board.PLAYER_2;

public class BoardView {

    private final Board board;
    private final LinearLayout layout;
    private final TextView text;

    public BoardView(@NonNull LinearLayout layout, @NonNull TextView text) {
        this.board = new Board();
        this.layout = layout;
        this.text = text;
        updateTextView();
    }

    public void createColumns() {
        for (int i = 0; i < board.getWidth(); i++) {
            ColumnLayout columnLayout = new ColumnLayout(layout.getContext(), board.getHeight());
            int column = i;
            columnLayout.setOnClickListener(view -> {
                if (board.playerInsertIntoColumn(column)) {
                    columnLayout.refresh(board.getColumnValues(column));
                    updateTextView();
                }
            });

            layout.addView(columnLayout);
        }
    }

    private void updateTextView() {
        switch (board.getCurrentPlayerID()) {
            case PLAYER_1:
                text.setText("Gracz 1");
                text.setTextColor(ContextCompat.getColor(layout.getContext(), R.color.player1));
                break;
            case PLAYER_2:
                text.setText("Gracz 2");
                text.setTextColor(ContextCompat.getColor(layout.getContext(), R.color.player2));
                break;
            default:
                text.setText("");
        }
    }
}
