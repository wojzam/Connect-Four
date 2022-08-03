package com.connect_four.app;

import android.app.Activity;
import android.widget.LinearLayout;

public class BoardView {

    private final Board board;
    private final Activity activity;
    private final LinearLayout boardLayout;

    public BoardView(Board board, Activity activity) {
        this.board = board;
        this.activity = activity;
        boardLayout = activity.findViewById(R.id.boardLayout);
    }

    public void createViews() {
        for (int i = 0; i < board.getWidth(); i++) {
            ColumnLayout columnLayout = new ColumnLayout(activity, board.getHeight());
            int finalJ = i;
            columnLayout.setOnClickListener(view -> {
                board.insertIntoColumn(finalJ, Board.PLAYER_1);
                columnLayout.refresh(board.getColumnValues(finalJ));
            });

            boardLayout.addView(columnLayout);
        }
    }
}
