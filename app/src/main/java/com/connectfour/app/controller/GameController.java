package com.connectfour.app.controller;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.connectfour.app.model.ModelInterface;
import com.connectfour.app.views.GameView;
import com.connectfour.app.views.ViewInterface;

public class GameController {

    private final ModelInterface model;
    private final ViewInterface view;


    public GameController(ModelInterface model, ConstraintLayout layout) {
        this.model = model;
        this.view = new GameView(model, this, layout);
    }

    @Override
    public void restart() {
        model.restart();
        view.update();
    }

    @Override
    public void columnClicked(int clickedColumn) {
        model.playTurn(clickedColumn);
    }

    @Override
    public void undoClicked() {
        model.undoTurn();
        view.update();
    }

}
