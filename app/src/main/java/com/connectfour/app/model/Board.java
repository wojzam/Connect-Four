package com.connectfour.app.model;

import java.util.Arrays;

import static com.connectfour.app.model.Disk.EMPTY;
import static com.connectfour.app.model.Disk.PLAYER_1;
import static com.connectfour.app.model.Disk.PLAYER_2;

public class Board {

    public static final int WIDTH_DEFAULT = 7;
    public static final int HEIGHT_DEFAULT = 6;
    private final Disk[][] values;
    private final int width;
    private final int height;
    private Disk currentPlayerDisk;

    public Board() {
        this(WIDTH_DEFAULT, HEIGHT_DEFAULT);
    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.values = new Disk[width][height];
        reset();
    }

    public Board(Board copy) {
        this.width = copy.getWidth();
        this.height = copy.getHeight();
        this.values = copy.getValues();
        this.currentPlayerDisk = copy.getCurrentPlayerDisk();
    }

    public void reset() {
        currentPlayerDisk = PLAYER_1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                values[i][j] = EMPTY;
            }
        }
    }

    public void changePlayer() {
        currentPlayerDisk = (currentPlayerDisk == PLAYER_1) ? PLAYER_2 : PLAYER_1;
    }

    public void print() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(values[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean insertIntoColumn(int column, Disk value) {
        int index = findFirstEmptyIndex(column);
        if (index >= 0) {
            values[column][index] = value;
            return true;
        }
        return false;
    }

    public boolean insertIntoColumn(int column) {
        return insertIntoColumn(column, currentPlayerDisk);
    }

    public void removeTopDiskFromColumn(int column) {
        for (int i = height - 1; i >= 0; i--) {
            if (values[column][i] != EMPTY) {
                values[column][i] = EMPTY;
                return;
            }
        }
    }

    public boolean currentPlayerWonGame() {
        Disk id = currentPlayerDisk;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 3; j++) {
                if (values[i][j] == id && values[i][j + 1] == id && values[i][j + 2] == id && values[i][j + 3] == id) {
                    return true;
                }
            }
        }

        for (int i = 0; i < width - 3; i++) {
            for (int j = 0; j < height; j++) {
                if (values[i][j] == id && values[i + 1][j] == id && values[i + 2][j] == id && values[i + 3][j] == id) {
                    return true;
                }
            }
        }

        for (int i = 0; i < width - 3; i++) {
            for (int j = 0; j < height - 3; j++) {
                if (values[i][j] == id && values[i + 1][j + 1] == id && values[i + 2][j + 2] == id && values[i + 3][j + 3] == id) {
                    return true;
                }
            }
        }

        for (int i = 3; i < width; i++) {
            for (int j = 0; j < height - 3; j++) {
                if (values[i][j] == id && values[i - 1][j + 1] == id && values[i - 2][j + 2] == id && values[i - 3][j + 3] == id) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canInsertInColumn(int column) {
        for (int i = 0; i < height; i++) {
            if (values[column][i] == EMPTY) {
                return true;
            }
        }
        return false;
    }

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

    public Disk[][] getValues() {
        Disk[][] valuesCopy = new Disk[width][height];
        for (int i = 0; i < width; i++) {
            valuesCopy[i] = getColumnValues(i);
        }
        return valuesCopy;
    }

    public Disk[] getColumnValues(int column) {
        return Arrays.copyOf(values[column], height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Disk getCurrentPlayerDisk() {
        return currentPlayerDisk;
    }

    @Override
    public int hashCode() {
        Disk[] flatArray = new Disk[width * height];
        int index = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                flatArray[index++] = values[i][j];
            }
        }
        return Arrays.hashCode(flatArray);
    }

    public int hashCodeFlippedHorizontally() {
        Disk[] flipped = new Disk[width * height];
        int index = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                flipped[index++] = values[width - i - 1][j];
            }
        }

        return Arrays.hashCode(flipped);
    }

    private int findFirstEmptyIndex(int column) {
        for (int i = 0; i < height; i++) {
            if (values[column][i] == EMPTY) {
                return i;
            }
        }
        return -1;
    }
}
