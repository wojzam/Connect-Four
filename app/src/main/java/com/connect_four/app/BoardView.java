package com.connect_four.app;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class BoardView extends LinearLayout {

    private final Context context;
    private final Board board;
    private final ArrayList<ColumnLayout> columns = new ArrayList<>();

    public BoardView(Context context, Board board) {
        super(context);
        this.context = context;
        this.board = board;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        createColumns();
    }

    public ArrayList<ColumnLayout> getColumns() {
        return columns;
    }

    public void refreshColumn(int column) {
        columns.get(column).refresh(board.getColumnValues(column));
    }

    public void refresh() {
        for (int i = 0; i < columns.size(); i++) {
            refreshColumn(i);
        }
    }

    public void createColumns() {
        for (int i = 0; i < board.getWidth(); i++) {
            ColumnLayout columnLayout = new ColumnLayout(context, board.getHeight());
            addView(columnLayout);
            columns.add(columnLayout);
        }
    }

}
