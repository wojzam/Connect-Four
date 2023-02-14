package com.connectfour.app.model;

import com.connectfour.app.views.GameObserver;

public interface GameModelInterface {

    void addGameObserver(GameObserver observer);

    void restart();

    void columnClickAction(int index);

    void undoTurn();

    Board getBoard();
}
