package com.connect_four.app.controller;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.connect_four.app.model.GameModelInterface;
import com.connect_four.app.views.ColumnLayout;
import com.connect_four.app.views.GameView;

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
