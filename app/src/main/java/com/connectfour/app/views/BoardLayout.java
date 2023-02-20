package com.connectfour.app.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.connectfour.app.model.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardLayout extends LinearLayout {

    private final Board board;
    private final List<ColumnLayout> columns = new ArrayList<>();

    public BoardLayout(Context context) {
        super(context);
        throw new UnsupportedOperationException("This constructor is not supported");
    }

    public BoardLayout(@NonNull Context context, @NonNull Board board) {
        super(context);
        this.board = board;
        setId(View.generateViewId());
        setOrientation(HORIZONTAL);
        createAndAddColumns(context);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            for (int columnIndex : board.getAvailableColumns()) {
                columns.get(columnIndex).setEnabled(true);
            }
        } else {
            columns.forEach(columnLayout -> columnLayout.setEnabled(false));
        }
    }

    public void update() {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).update(board.getColumnValues(i));
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
