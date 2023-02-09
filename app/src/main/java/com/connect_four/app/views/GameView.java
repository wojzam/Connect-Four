package com.connect_four.app.views;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;

import com.connect_four.app.R;
import com.connect_four.app.controller.GameController;
import com.connect_four.app.model.Board;
import com.connect_four.app.model.GameModelInterface;

public class GameView implements GameObserver {

    private final Board board;
    private final GameController controller;
    private final BoardLayout boardLayout;
    private final GameStateTextView gameStateText;
    private final AppCompatButton newGameButton;
    private final AppCompatButton undoButton;

    public GameView(GameModelInterface model, GameController controller, LinearLayout mainLayout) {
        this.board = model.getBoard();
        this.controller = controller;
        this.boardLayout = new BoardLayout(mainLayout.getContext(), board);
        this.gameStateText = new GameStateTextView(mainLayout.getContext());
        this.newGameButton = new AppCompatButton(mainLayout.getContext());
        this.undoButton = new AppCompatButton(mainLayout.getContext());

        arrangeViews(mainLayout);

        model.addGameObserver(this);

        newGameButton.setOnClickListener(view -> controller.restart());
        undoButton.setOnClickListener(view -> controller.undoButtonClicked());
    }

    public static int dpToPixel(float dp, Context context) {
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
        gameStateText.update(board);
    }

    private void arrangeViews(LinearLayout mainLayout) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        int margin = dpToPixel(15, mainLayout.getContext());
        layoutParams.setMargins(margin, 0, margin, 0);

        newGameButton.setText(R.string.new_game);
        newGameButton.setBackgroundResource(R.drawable.button_bg);
        newGameButton.setLayoutParams(layoutParams);

        undoButton.setText(R.string.undo);
        undoButton.setBackgroundResource(R.drawable.button_bg);
        undoButton.setLayoutParams(layoutParams);

        LinearLayout buttonsLayout = new LinearLayout(mainLayout.getContext());
        buttonsLayout.setGravity(Gravity.CENTER);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.addView(undoButton);
        buttonsLayout.addView(newGameButton);

        mainLayout.addView(boardLayout);
        mainLayout.addView(gameStateText);
        mainLayout.addView(buttonsLayout);
    }
}
