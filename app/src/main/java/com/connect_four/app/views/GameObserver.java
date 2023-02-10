package com.connect_four.app.views;

public interface GameObserver {

    void updateBoard();

    void updateColumn(int index);

    void updateGameStatus();

    void setUndoEnabled(boolean enabled);

    void setEnabled(boolean enabled);
}
