package com.connectfour.app.model;

import com.connectfour.app.views.GameObserver;

public interface GameModelInterface {

    void addGameObserver(GameObserver observer);

    void restart();

    void playTurn(int chosenColumn);

    void undoTurn();

    boolean canUndo();

    Board getBoard();
}
