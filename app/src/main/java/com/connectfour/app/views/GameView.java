package com.connectfour.app.views;

import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.connectfour.app.R;
import com.connectfour.app.controller.GameController;
import com.connectfour.app.model.GameModelInterface;

public class GameView implements GameObserver {

    private final GameModelInterface model;
    private final GameController controller;
    private final BoardLayout boardLayout;
    private final GameStateTextView gameStateText;
    private final AppCompatButton undoButton;
    private final AppCompatButton newGameButton;

    public GameView(GameModelInterface model, GameController controller, ConstraintLayout mainLayout) {
        this.model = model;
        this.controller = controller;
        this.boardLayout = new BoardLayout(mainLayout.getContext(), model.getBoard());
        this.gameStateText = new GameStateTextView(mainLayout.getContext());
        this.undoButton = new CustomButton(mainLayout.getContext(), R.string.undo);
        this.newGameButton = new CustomButton(mainLayout.getContext(), R.string.new_game);

        arrangeViews(mainLayout);
        update();

        model.addGameObserver(this);

        boardLayout.columnsSetOnClickListener(this::columnClicked);
        newGameButton.setOnClickListener(view -> controller.restart());
        undoButton.setOnClickListener(view -> controller.undoClicked());
    }

    @Override
    public void update() {
        boardLayout.update();
        gameStateText.update(model.getBoard());
        setUndoEnabled(model.canUndo());
    }

    @Override
    public void requestPlayerMove() {
        boardLayout.setEnabled(true);
    }

    private void columnClicked(View view) {
        int clickedColumnIndex = ((ColumnLayout) view).getIndex();
        boardLayout.setEnabled(false);
        controller.columnClicked(clickedColumnIndex);
    }

    private void setUndoEnabled(boolean enabled) {
        undoButton.setEnabled(enabled);
        if (enabled) {
            undoButton.setBackgroundResource(R.drawable.button_bg);
        } else {
            undoButton.setBackgroundResource(R.drawable.button_disabled_bg);
        }
    }

    private void arrangeViews(ConstraintLayout mainLayout) {
        int buttonsMargin = (int) mainLayout.getResources().getDimension(R.dimen.button_margin);

        mainLayout.addView(boardLayout);
        mainLayout.addView(gameStateText);
        mainLayout.addView(undoButton);
        mainLayout.addView(newGameButton);

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(boardLayout.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, 0);
        set.connect(boardLayout.getId(), ConstraintSet.BOTTOM, gameStateText.getId(), ConstraintSet.TOP, 0);
        set.connect(boardLayout.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, 0);
        set.connect(boardLayout.getId(), ConstraintSet.RIGHT, mainLayout.getId(), ConstraintSet.RIGHT, 0);

        set.connect(gameStateText.getId(), ConstraintSet.BOTTOM, undoButton.getId(), ConstraintSet.TOP, 0);
        set.connect(gameStateText.getId(), ConstraintSet.TOP, boardLayout.getId(), ConstraintSet.BOTTOM, 0);
        set.connect(gameStateText.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, 0);
        set.connect(gameStateText.getId(), ConstraintSet.RIGHT, mainLayout.getId(), ConstraintSet.RIGHT, 0);

        set.connect(undoButton.getId(), ConstraintSet.BOTTOM, mainLayout.getId(), ConstraintSet.BOTTOM, 0);
        set.connect(undoButton.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, buttonsMargin);
        set.connect(undoButton.getId(), ConstraintSet.RIGHT, newGameButton.getId(), ConstraintSet.LEFT, buttonsMargin);

        set.connect(newGameButton.getId(), ConstraintSet.BOTTOM, mainLayout.getId(), ConstraintSet.BOTTOM, 0);
        set.connect(newGameButton.getId(), ConstraintSet.LEFT, undoButton.getId(), ConstraintSet.RIGHT, 0);
        set.connect(newGameButton.getId(), ConstraintSet.RIGHT, mainLayout.getId(), ConstraintSet.RIGHT, buttonsMargin);

        set.applyTo(mainLayout);
    }
}
