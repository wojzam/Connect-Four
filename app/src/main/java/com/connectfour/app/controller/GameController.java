package com.connectfour.app.controller;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.connectfour.app.model.ModelInterface;
import com.connectfour.app.views.GameView;
import com.connectfour.app.views.ViewInterface;

/**
 * The {@code GameController} class represents the Controller component of the MVC design pattern for the Connect Four game.
 * Its main role is to mediate between the {@link ModelInterface} (model) and the {@link ViewInterface} (view)
 * to handle user interactions and update the game state accordingly.
 *
 * @see ModelInterface
 * @see ViewInterface
 */
public class GameController implements ControllerInterface {

    private final ModelInterface model;
    private final ViewInterface view;

    /**
     * Constructs a new {@code GameController} object with the given {@link ModelInterface} and {@link ConstraintLayout}.
     * This constructor also creates {@link GameView} which creates all game views
     * in the specified {@code layout} parameter.
     *
     * @param model  the GameModelInterface object to use for the game logic
     * @param layout the ConstraintLayout object to use as the game view layout
     */
    public GameController(ModelInterface model, ConstraintLayout layout) {
        this.model = model;
        this.view = new GameView(model, this, layout);
    }

    /**
     * Restarts the game by restarting the model and updating the view.
     */
    @Override
    public void restart() {
        model.restart();
        view.update();
    }

    /**
     * Handles a column clicked event by playing the turn in the chosen column.
     *
     * @param clickedColumn the index of the clicked column
     */
    @Override
    public void columnClicked(int clickedColumn) {
        model.playTurn(clickedColumn);
    }

    /**
     * Handles an undo button clicked event by undoing the last turn and updating the view.
     */
    @Override
    public void undoClicked() {
        model.undoTurn();
        view.update();
    }

}
