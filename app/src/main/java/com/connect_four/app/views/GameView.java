package com.connect_four.app.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.connect_four.app.R;
import com.connect_four.app.controller.GameController;
import com.connect_four.app.model.Board;
import com.connect_four.app.model.Disk;
import com.connect_four.app.model.GameModelInterface;

import static com.connect_four.app.model.Disk.PLAYER_1;

public class GameView implements GameObserver {

    private final Board board;
    private final GameController controller;
    private final BoardLayout boardLayout;
    private final TextView text;
    private final AppCompatButton undoButton;
    private final int colorPlayer1;
    private final int colorPlayer2;
    private final int colorNeutral;

    public GameView(GameModelInterface model, GameController controller, LinearLayout mainLayout) {
        this.board = model.getBoard();
        this.controller = controller;
        this.boardLayout = new BoardLayout(mainLayout.getContext(), board);
        this.text = new TextView(mainLayout.getContext());
        this.undoButton = new AppCompatButton(mainLayout.getContext());
        this.colorPlayer1 = ContextCompat.getColor(mainLayout.getContext(), R.color.player1);
        this.colorPlayer2 = ContextCompat.getColor(mainLayout.getContext(), R.color.player2);
        this.colorNeutral = ContextCompat.getColor(mainLayout.getContext(), R.color.white);
        arrangeViews(mainLayout);

        model.addGameObserver(this);

        undoButton.setOnClickListener(view -> controller.undoButtonClicked());
    }

    private static int dpToPixel(float dp, Context context) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void updateBoard() {
        boardLayout.refresh();
    }

    @Override
    public void updateColumn(int index) {
        boardLayout.refreshColumn(index);
    }

    @Override
    public void enableTurn() {
        boardLayout.columnsSetOnClickListener(view -> controller.columnClickAction((ColumnLayout) view));
        undoButton.setEnabled(true);
    }

    @Override
    public void disableTurn() {
        boardLayout.columnsRemoveOnClickListener();
        undoButton.setEnabled(false);
    }

    @Override
    public void updateGameStatus() {
        final Disk currentPlayerID = board.getCurrentPlayerDisk();
        if (board.currentPlayerWonGame()) {
            if (currentPlayerID == PLAYER_1) {
                text.setText(R.string.player_1_won);
                text.setTextColor(colorPlayer1);
            } else {
                text.setText(R.string.player_2_won);
                text.setTextColor(colorPlayer2);
            }
        } else if (board.isFull()) {
            text.setText(R.string.tie_info);
            text.setTextColor(colorNeutral);
        } else {
            if (currentPlayerID == PLAYER_1) {
                text.setText(R.string.player_1_turn);
                text.setTextColor(colorPlayer1);
            } else {
                text.setText(R.string.player_2_turn);
                text.setTextColor(colorPlayer2);
            }
        }
    }

    private void arrangeViews(LinearLayout mainLayout) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(0, dpToPixel(30, mainLayout.getContext()), 0, 0);

        text.setLayoutParams(layoutParams);
        text.setTypeface(Typeface.SERIF);
        text.setTextSize(25);

        undoButton.setText(R.string.undo);
        undoButton.setLayoutParams(layoutParams);
        undoButton.setBackgroundResource(R.drawable.button_bg);

        mainLayout.addView(boardLayout);
        mainLayout.addView(text);
        mainLayout.addView(undoButton);
    }
}