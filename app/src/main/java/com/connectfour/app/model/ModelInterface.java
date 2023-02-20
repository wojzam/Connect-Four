package com.connectfour.app.model;

import com.connectfour.app.views.ViewInterface;

public interface ModelInterface {

    void addGameObserver(ViewInterface observer);

    void restart();

    void playTurn(int chosenColumn);

    void undoTurn();

    boolean canUndo();

    Board getBoard();
}
