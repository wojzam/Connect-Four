package com.connectfour.app.controller;

/**
 * This interface represents the <b>controller</b> in the Model-View-Controller pattern for the Connect Four game.
 */
public interface ControllerInterface {

    /**
     * Restarts the game.
     */
    void restart();

    /**
     * Handles a column clicked event.
     *
     * @param clickedColumn the index of the clicked column
     */
    void columnClicked(int clickedColumn);

    /**
     * Handles an undo button clicked event.
     */
    void undoClicked();
}
