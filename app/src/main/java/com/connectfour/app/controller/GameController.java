package com.connectfour.app.controller;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.connectfour.app.model.GameModelInterface;
import com.connectfour.app.views.ColumnLayout;
import com.connectfour.app.views.GameView;

public class GameController {

    private final GameModelInterface model;
    private final GameView view;

    public GameController(GameModelInterface model, ConstraintLayout layout) {
        this.model = model;
        this.view = new GameView(model, this, layout);
    }

    public void restart() {
        model.restart();
        view.updateBoard();
        view.updateGameStatus();
    }

    public void columnClickAction(ColumnLayout clickedColumn) {
        model.columnClickAction(clickedColumn.getIndex());
    }

    public void undoButtonClicked() {
        model.undoTurn();
        view.updateBoard();
        view.updateGameStatus();
    }

}
