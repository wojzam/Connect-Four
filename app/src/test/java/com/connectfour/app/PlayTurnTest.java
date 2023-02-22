package com.connectfour.app;

import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;
import com.connectfour.app.model.commands.PlayTurn;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.connectfour.app.BoardTestData.EMPTY_BOARD;
import static com.connectfour.app.BoardTestData.FULL_BOARD;
import static com.connectfour.app.BoardTestData.THREE_IN_ROW_VERTICAL;
import static com.connectfour.app.model.Disk.EMPTY;
import static com.connectfour.app.model.Disk.PLAYER_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayTurnTest {

    @Test
    void shouldInsertDisk() {
        Board board = new Board(2, 2);

        new PlayTurn(board, 0).execute();

        Disk[][] expectedValues = {{PLAYER_1, EMPTY}, {EMPTY, EMPTY}};
        assertTrue(Arrays.deepEquals(board.getValues(), expectedValues));
    }

    @Test
    void shouldChangePlayer() {
        Board board = new Board(2, 2);
        Disk previousPlayer = board.getCurrentPlayerDisk();

        new PlayTurn(board, 0).execute();

        assertNotEquals(previousPlayer, board.getCurrentPlayerDisk());
    }

    @Test
    void shouldNotChangePlayer_whenGameWon() {
        Board board = new Board(THREE_IN_ROW_VERTICAL);
        Disk previousPlayer = board.getCurrentPlayerDisk();

        new PlayTurn(board, 0).execute();

        assertEquals(previousPlayer, board.getCurrentPlayerDisk());
    }

    @Test
    void shouldNotChangePlayer_whenGameIsTie() {
        Board board = new Board(FULL_BOARD);
        board.removeTopDiskFromColumn(0);
        Disk previousPlayer = board.getCurrentPlayerDisk();

        new PlayTurn(board, 0).execute();

        assertEquals(previousPlayer, board.getCurrentPlayerDisk());
    }

    @Test
    void shouldUndoInsert() {
        Board board = new Board(EMPTY_BOARD);
        PlayTurn playTurn = new PlayTurn(board, 0);

        playTurn.execute();
        playTurn.undo();

        assertTrue(Arrays.deepEquals(board.getValues(), EMPTY_BOARD));
    }

    @Test
    void shouldUndoPlayerChange() {
        Board board = new Board(EMPTY_BOARD);
        Disk previousPlayer = board.getCurrentPlayerDisk();
        PlayTurn playTurn = new PlayTurn(board, 0);

        playTurn.execute();
        playTurn.undo();

        assertEquals(previousPlayer, board.getCurrentPlayerDisk());
    }

    @Test
    void shouldNotUndoPlayerChange_whenGameWon() {
        Board board = new Board(THREE_IN_ROW_VERTICAL);
        Disk previousPlayer = board.getCurrentPlayerDisk();
        PlayTurn playTurn = new PlayTurn(board, 0);

        playTurn.execute();
        playTurn.undo();

        assertEquals(previousPlayer, board.getCurrentPlayerDisk());
    }

    @Test
    void shouldNotUndoPlayerChange_whenGameIsTie() {
        Board board = new Board(FULL_BOARD);
        board.removeTopDiskFromColumn(0);
        Disk previousPlayer = board.getCurrentPlayerDisk();
        PlayTurn playTurn = new PlayTurn(board, 0);

        playTurn.execute();
        playTurn.undo();

        assertEquals(previousPlayer, board.getCurrentPlayerDisk());
    }

    @Test
    void shouldGetIfGameInProgress_whenGameIsStillInProgress() {
        Board board = new Board(2, 2);
        PlayTurn playTurn = new PlayTurn(board, 0);

        playTurn.execute();

        assertTrue(playTurn.isGameInProgress());
    }

    @Test
    void shouldGetIfGameInProgress_whenGameWon() {
        Board board = new Board(THREE_IN_ROW_VERTICAL);
        PlayTurn playTurn = new PlayTurn(board, 0);

        playTurn.execute();

        assertFalse(playTurn.isGameInProgress());
    }

    @Test
    void shouldGetIfGameInProgress_whenGameIsTie() {
        Board board = new Board(FULL_BOARD);
        board.removeTopDiskFromColumn(0);
        PlayTurn playTurn = new PlayTurn(board, 0);

        playTurn.execute();

        assertFalse(playTurn.isGameInProgress());
    }
}
