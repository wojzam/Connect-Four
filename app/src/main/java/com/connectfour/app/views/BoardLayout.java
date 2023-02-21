package com.connectfour.app.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.connectfour.app.model.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BoardLayout} represents the layout of the {@link Board}, which is composed of several columns.
 * Each column is represented by a {@link ColumnLayout}.
 *
 * @see Board
 * @see ColumnLayout
 */
public class BoardLayout extends LinearLayout {

    private final Board board;
    private final List<ColumnLayout> columns = new ArrayList<>();

    /**
     * This constructor is not supported.
     *
     * @param context the context to use
     * @throws UnsupportedOperationException always thrown, as this constructor is not supported
     */
    public BoardLayout(Context context) {
        super(context);
        throw new UnsupportedOperationException("This constructor is not supported");
    }

    /**
     * Constructs a new instance of {@code BoardLayout}.
     *
     * @param context the context to use
     * @param board   the game board to represent
     * @see Board
     */
    public BoardLayout(@NonNull Context context, @NonNull Board board) {
        super(context);
        this.board = board;
        setId(View.generateViewId());
        setOrientation(HORIZONTAL);
        createAndAddColumns(context);
    }

    /**
     * Enables or disables the columns based on the availability of moves in each column.
     * Only the columns where a disk can be inserted can be enabled.
     *
     * @param enabled true if the columns should be enabled, false if they should be disabled
     */
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

    /**
     * Updates the disks in the columns based on the state of the {@link Board}.
     */
    public void update() {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).update(board.getColumnValues(i));
        }
    }

    /**
     * Sets an OnClickListener for each column.
     *
     * @param onClickListener the OnClickListener to set for each column
     */
    public void columnsSetOnClickListener(OnClickListener onClickListener) {
        columns.forEach(columnLayout -> columnLayout.setOnClickListener(onClickListener));
    }

    /**
     * Creates and adds a {@link ColumnLayout} for each column in the {@link Board}.
     *
     * @param context the context to use
     */
    private void createAndAddColumns(Context context) {
        for (int i = 0; i < board.getWidth(); i++) {
            ColumnLayout columnLayout = new ColumnLayout(context, board.getColumnValues(i), i);
            addView(columnLayout);
            columns.add(columnLayout);
        }
    }
}
