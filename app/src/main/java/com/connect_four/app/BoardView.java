package com.connect_four.app;

import android.widget.LinearLayout;

public class BoardView {

    private final Board board;
    private final LinearLayout layout;

    public BoardView(Board board, LinearLayout layout) {
        this.board = board;
        this.layout = layout;
    }

    public void createViews() {
        for (int i = 0; i < board.getWidth(); i++) {
            ColumnLayout columnLayout = new ColumnLayout(layout.getContext(), board.getHeight());
            int finalJ = i;
            columnLayout.setOnClickListener(view -> {
                board.insertIntoColumn(finalJ, Board.PLAYER_1);
                columnLayout.refresh(board.getColumnValues(finalJ));
            });

            layout.addView(columnLayout);
        }
    }
}
