package com.connectfour.app.views;

import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.connectfour.app.R;
import com.connectfour.app.controller.ControllerInterface;
import com.connectfour.app.model.ModelInterface;

/**
 * The {@code GameView} class represents the View component of the MVC design pattern for the Connect Four game.
 * It displays the game board, game state text, and provides buttons for undo and starting a new game.
 * The {@code GameView} is observing the model, which allows it to get notified when it should be updated.
 *
 * @see ModelInterface
 * @see ControllerInterface
 */
public class GameView implements ViewInterface {
    private final ModelInterface model;
    private final ControllerInterface controller;
    private final BoardLayout boardLayout;
    private final GameStateTextView gameStateText;
    private final AppCompatButton undoButton;
    private final AppCompatButton newGameButton;

    /**
     * Constructs a new {@code GameView} with the given {@link ModelInterface},
     * {@link ControllerInterface} and {@link ConstraintLayout}.
     * Initializes and arranges all the views within the specified layout, and registers
     * this object as an observer of the model.
     *
     * @param model      the GameModelInterface to be used by this GameView
     * @param controller the GameController to be used by this GameView
     * @param mainLayout the ConstraintLayout to use as the game view layout
     */
    public GameView(ModelInterface model, ControllerInterface controller, ConstraintLayout mainLayout) {
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

    /**
     * Updates all view components to reflect the current state of the game model.
     */
    @Override
    public void update() {
        boardLayout.update();
        gameStateText.update(model.getBoard());
        setUndoEnabled(model.canUndo());
    }

    /**
     * Enables user interaction with the game board to allow the player to make a move.
     * This method should be called before every player turn,
     * because of how the {@link #columnClicked(View)} method works.
     *
     * @see #columnClicked(View)
     */
    @Override
    public void requestPlayerMove() {
        boardLayout.setEnabled(true);
    }

    /**
     * Handles the event when a column is clicked by the player, by forwarding the column index to the game controller to make the move.
     * It also disables user interaction with the game board until {@link #requestPlayerMove()} is called again.
     *
     * @param view the view representing the clicked column
     */
    private void columnClicked(View view) {
        int clickedColumnIndex = ((ColumnLayout) view).getIndex();
        boardLayout.setEnabled(false);
        controller.columnClicked(clickedColumnIndex);
    }

    /**
     * Sets the enabled state and background of the undo button based on whether an undo operation is currently possible.
     *
     * @param enabled whether an undo operation is currently possible
     */
    private void setUndoEnabled(boolean enabled) {
        undoButton.setEnabled(enabled);
        if (enabled) {
            undoButton.setBackgroundResource(R.drawable.button_bg);
        } else {
            undoButton.setBackgroundResource(R.drawable.button_disabled_bg);
        }
    }

    /**
     * Arranges the views within the layout.
     *
     * @param mainLayout the main layout used to display game views
     */
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
