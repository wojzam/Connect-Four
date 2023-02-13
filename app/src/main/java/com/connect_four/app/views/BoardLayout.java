package com.connect_four.app.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.connect_four.app.model.Board;

import java.util.ArrayList;

public class BoardLayout extends LinearLayout {

    private final Board board;
    private final ArrayList<ColumnLayout> columns = new ArrayList<>();

    public BoardLayout(Context context) {
        super(context);
        throw new UnsupportedOperationException("This constructor is not supported");
    }

    public BoardLayout(Context context, Board board) {
        super(context);
        this.board = board;
        setId(View.generateViewId());
        setOrientation(HORIZONTAL);
        createAndAddColumns(context);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        columns.forEach(columnLayout -> columnLayout.setEnabled(enabled));
    }

    public void refreshColumn(int column) {
        columns.get(column).refresh(board.getColumnValues(column));
    }

    public void refresh() {
        for (int i = 0; i < columns.size(); i++) {
            refreshColumn(i);
        }
    }

    public void columnsSetOnClickListener(OnClickListener onClickListener) {
        columns.forEach(columnLayout -> columnLayout.setOnClickListener(onClickListener));
    }

    private void createAndAddColumns(Context context) {
        for (int i = 0; i < board.getWidth(); i++) {
            ColumnLayout columnLayout = new ColumnLayout(context, board.getColumnValues(i), i);
            addView(columnLayout);
            columns.add(columnLayout);
        }
    }
}
