package com.connectfour.app.model;

import com.connectfour.app.views.ViewInterface;

/**
 * This interface represents the <b>model</b> in the Model-View-Controller pattern for the Connect Four game.
 */
public interface ModelInterface {

    /**
     * Add an observer to the list of observers that will be notified of changes in the game state.
     *
     * @param observer the observer to add to the list
     */
    void addGameObserver(ViewInterface observer);

    /**
     * Restart the game.
     */
    void restart();

    /**
     * Play a turn by dropping a disk in the specified column.
     *
     * @param chosenColumn the index of the column to drop the disk
     */
    void playTurn(int chosenColumn);

    /**
     * Undo the last turn that was played.
     */
    void undoTurn();

    /**
     * @return true if undo is currently possible
     */
    boolean canUndo();

    /**
     * @return the game {@link Board}
     */
    Board getBoard();
}
