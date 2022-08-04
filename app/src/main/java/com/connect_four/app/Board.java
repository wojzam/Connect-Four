package com.connect_four.app;

import java.util.Arrays;

public class Board {

    public static final byte EMPTY = 0;
    public static final byte PLAYER_1 = 1;
    public static final byte PLAYER_2 = 2;
    private final byte[][] values;
    private final int width;
    private final int height;

    private byte currentPlayerID;

    public Board() {
        this(7, 6);
    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.values = new byte[width][height];
        this.currentPlayerID = PLAYER_1;
        clearBoard();
    }

    public void clearBoard() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                values[i][j] = EMPTY;
            }
        }
    }

    public void print() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(values[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean insertIntoColumn(int column, byte value) {
        int index = findFirstEmptyIndex(column);
        if (index >= 0) {
            values[column][index] = value;
            return true;
        }
        return false;
    }

    public boolean insertIntoColumn(int column) {
        return insertIntoColumn(column, currentPlayerID);
    }

    public boolean wonGame() {
        byte id = currentPlayerID;
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

    public boolean isFull(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (values[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public byte[][] getValues() {
        byte[][] valuesCopy = new byte[width][height];
        for (int i = 0; i < width; i++) {
            valuesCopy[i] = getColumnValues(i);
        }
        return valuesCopy;
    }

    public byte[] getColumnValues(int column) {
        return Arrays.copyOf(values[column], height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte getCurrentPlayerID() {
        return currentPlayerID;
    }

    public void changePlayer() {
        if (currentPlayerID == PLAYER_1) {
            currentPlayerID = PLAYER_2;
        } else {
            currentPlayerID = PLAYER_1;
        }
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
