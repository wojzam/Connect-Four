package com.connectfour.app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.connectfour.app.model.Disk.EMPTY;
import static com.connectfour.app.model.Disk.PLAYER_1;
import static com.connectfour.app.model.Disk.PLAYER_2;

/**
 * The {@code Board} class represents the Connect Four game board.
 * The board is a two-dimensional array of {@link Disk} objects.
 * It keeps track of the current player, and provides methods for making moves and checking for end of the game.
 */
public class Board {

    public static final int WIDTH_DEFAULT = 7;
    public static final int HEIGHT_DEFAULT = 6;
    protected final Disk[][] values;
    protected final int width;
    protected final int height;
    protected Disk currentPlayerDisk;

    /**
     * Constructs a new {@code Board} object with the default dimensions.
     * It uses {@link #Board(int width, int height)} constructor with
     * width set to {@value WIDTH_DEFAULT}, and height set to {@value HEIGHT_DEFAULT}.
     */
    public Board() {
        this(WIDTH_DEFAULT, HEIGHT_DEFAULT);
    }

    /**
     * Constructs a new {@code Board} object with the specified dimensions.
     * It also calls the {@link #reset} method to set the board to its initial state.
     *
     * @param width  the width of the board
     * @param height the height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.values = new Disk[width][height];
        reset();
    }

    /**
     * Constructs a new {@code Board} object that is a copy of an existing board.
     *
     * @param copy the board to copy
     */
    public Board(Board copy) {
        this.width = copy.getWidth();
        this.height = copy.getHeight();
        this.values = copy.getValues();
        this.currentPlayerDisk = copy.getCurrentPlayerDisk();
    }

    /**
     * Resets the board to its initial state, with all disks set to the {@code EMPTY}.
     * The current player's disk is set to {@code PLAYER_1}.
     */
    public void reset() {
        currentPlayerDisk = PLAYER_1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                values[i][j] = EMPTY;
            }
        }
    }

    /**
     * Changes the current player to the other player.
     */
    public void changePlayer() {
        currentPlayerDisk = (currentPlayerDisk == PLAYER_1) ? PLAYER_2 : PLAYER_1;
    }

    /**
     * Prints the board to the console. It can be useful for debugging.
     */
    public void print() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(values[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Finds the index of the first empty slot in the specified column of the game board.
     *
     * @param column the column to search for an empty slot
     * @return the index of the first empty slot in the column, or -1 if the column is full
     */
    private int findFirstEmptyIndex(int column) {
        for (int i = 0; i < height; i++) {
            if (values[column][i] == EMPTY) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Inserts a {@link Disk} into a column on the board.
     *
     * @param column the column to insert into
     * @param disk   the disk to insert
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insertIntoColumn(int column, Disk disk) {
        int index = findFirstEmptyIndex(column);
        if (index >= 0) {
            values[column][index] = disk;
            return true;
        }
        return false;
    }

    /**
     * Inserts the current player's {@link Disk} into a column on the board.
     *
     * @param column the column to insert into
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insertIntoColumn(int column) {
        return insertIntoColumn(column, currentPlayerDisk);
    }

    /**
     * Removes the top disk from a column on the board.
     *
     * @param column the column to remove the disk from
     */
    public void removeTopDiskFromColumn(int column) {
        for (int i = height - 1; i >= 0; i--) {
            if (values[column][i] != EMPTY) {
                values[column][i] = EMPTY;
                return;
            }
        }
    }

    /**
     * Checks if a disk can be inserted in the specified column.
     *
     * @param column the column to check
     * @return true if a disk can be inserted in the specified column, false otherwise
     */
    public boolean canInsertInColumn(int column) {
        return findFirstEmptyIndex(column) != -1;
    }

    /**
     * @return a list of the columns in which a disk can be inserted
     */
    public List<Integer> getAvailableColumns() {
        List<Integer> availableColumns = new ArrayList<>(width);
        for (int i = 0; i < width; i++) {
            if (canInsertInColumn(i)) {
                availableColumns.add(i);
            }
        }
        return availableColumns;
    }

    /**
     * @return true if the board is full
     */
    public boolean isFull() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (values[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the current player has won the game and ignores the other player's disks for optimization.
     * To ensure that all wins are detected, it's recommended to call this method after every turn.
     *
     * @return true if the current player has won the game, false otherwise
     */
    public boolean currentPlayerWonGame() {
        Disk id = currentPlayerDisk;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (values[i][j] != id) continue;
                if (i < width - 3 && values[i + 1][j] == id && values[i + 2][j] == id && values[i + 3][j] == id) {
                    return true; // horizontal
                }
                if (j < height - 3 && values[i][j + 1] == id && values[i][j + 2] == id && values[i][j + 3] == id) {
                    return true; // vertical
                }
                if (i < width - 3 && j < height - 3 && values[i + 1][j + 1] == id && values[i + 2][j + 2] == id && values[i + 3][j + 3] == id) {
                    return true; // diagonal (positive slope)
                }
                if (i >= 3 && j < height - 3 && values[i - 1][j + 1] == id && values[i - 2][j + 2] == id && values[i - 3][j + 3] == id) {
                    return true; // diagonal (negative slope)
                }
            }
        }
        return false;
    }

    /**
     * @param column the column index to get the values of
     * @return a copy of the values of the specified column
     */
    public Disk[] getColumnValues(int column) {
        return Arrays.copyOf(values[column], height);
    }

    /**
     * @return a copy of the values of the board
     */
    public Disk[][] getValues() {
        Disk[][] valuesCopy = new Disk[width][height];
        for (int i = 0; i < width; i++) {
            valuesCopy[i] = getColumnValues(i);
        }
        return valuesCopy;
    }

    /**
     * @return width of the board
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of the board
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return current player's {@link Disk}
     */
    public Disk getCurrentPlayerDisk() {
        return currentPlayerDisk;
    }

    /**
     * Calculates and returns the unique hash value for the current state of the board.
     * The hash value is calculated by treating each cell of the board as a binary digit,
     * with a value of 1 if the cell contains player's disk, and a value of 0 if it is {@code EMPTY}.
     * The binary digits are concatenated to form two hash values, one for {@code PLAYER_1} disks
     * and another for {@code PLAYER_2} disks.
     *
     * @return a new {@link BoardHash} object uniquely representing the current state of the board
     * @see BoardHash
     */
    public BoardHash getBoardHash() {
        long hash1 = 0L;
        long hash2 = 0L;
        long multiplier = 1L;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (values[i][j] == PLAYER_1) {
                    hash1 += multiplier;
                } else if (values[i][j] == PLAYER_2) {
                    hash2 += multiplier;
                }
                multiplier *= 2L;
            }
        }

        return new BoardHash(hash1, hash2);
    }

}
