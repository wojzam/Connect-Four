package com.connect_four.app.model;

import com.connect_four.app.views.GameObserver;

public interface GameModelInterface {

    void addGameObserver(GameObserver observer);

    void restart();

    void columnClickAction(int index);

    void undoTurn();

    Board getBoard();
}
