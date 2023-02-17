package com.connectfour.app.controller;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.connectfour.app.model.GameModelInterface;
import com.connectfour.app.views.GameObserver;
import com.connectfour.app.views.GameView;

public class GameController {

    private final GameModelInterface model;
    private final GameObserver view;

    public GameController(GameModelInterface model, ConstraintLayout layout) {
        this.model = model;
        this.view = new GameView(model, this, layout);
    }

    public void restart() {
        model.restart();
        view.update();
    }

    public void columnClicked(int clickedColumn) {
        model.playTurn(clickedColumn);
    }

    public void undoClicked() {
        model.undoTurn();
        view.update();
    }

}
