package com.connectfour.app;

import com.connectfour.app.model.Board;
import com.connectfour.app.model.GameModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameModelTest {

    private static Settings settingsMultiplayer;
    private GameModel model;

    @BeforeAll
    static void setUpMock() {
        settingsMultiplayer = mock(Settings.class);
        when(settingsMultiplayer.isSinglePlayer()).thenReturn(false);
    }

    @BeforeEach
    void setUpModel() {
        model = new GameModel(settingsMultiplayer);
    }

    @Test
    void shouldRestart() {
        Board board = new Board();
        model.playTurn(0);
        model.playTurn(1);
        model.playTurn(2);

        model.restart();

        assertEquals(board, model.getBoard());
    }

    @Test
    void shouldPlayTurn() {
        Board board = new Board();
        board.insertIntoColumn(0);
        board.changePlayer();

        model.playTurn(0);

        assertEquals(board, model.getBoard());
    }

    @Test
    void shouldUndoTurn() {
        Board board = new Board();

        model.playTurn(0);
        model.undoTurn();

        assertEquals(board, model.getBoard());
    }

    @Test
    void shouldUndoTurnMultipleTimes() {
        Board board = new Board();
        board.insertIntoColumn(0);
        board.changePlayer();

        model.playTurn(0);
        model.playTurn(1);
        model.playTurn(1);
        model.undoTurn();
        model.undoTurn();

        assertEquals(board, model.getBoard());
    }

    @Test
    void shouldDisableUndo_whenNoMovesMade() {
        assertFalse(model.canUndo());
    }

    @Test
    void shouldAllowUndo_afterTurnPlayed() {
        model.playTurn(0);

        assertTrue(model.canUndo());
    }
}
