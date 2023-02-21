package com.connectfour.app.views;

/**
 * This interface represents the <b>view</b> in the Model-View-Controller pattern for the Connect Four game.
 */
public interface ViewInterface {

    /**
     * Notifies the view that it should update its display to reflect changes in the model.
     */
    void update();

    /**
     * Enables the user interface components that allow the player to make a move.
     */
    void requestPlayerMove();
}
