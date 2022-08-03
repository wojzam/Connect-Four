package com.connect_four.app;

import java.util.Arrays;

public class Board {

    public static final byte EMPTY = 0;
    public static final byte PLAYER_1 = 1;
    public static final byte PLAYER_2 = 2;
    private final byte[][] values;
    private final int width;
    private final int height;

    public Board() {
        this(7, 6);
    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        values = new byte[width][height];
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

    public boolean insertIntoColumn(int column, int value) {
        int index = findFirstEmptyIndex(column);
        if (index >= 0) {
            values[column][index] = (byte) value;
            return true;
        }
        return false;
    }

    public byte[][] getValues() {
        byte[][] valuesCopy = new byte[width][height];
        for (int i = 0; i < width; i++) {
            valuesCopy[i] = getColumnValues(i);
        }
        return valuesCopy;
    }

    public byte[]getColumnValues(int column) {
        return Arrays.copyOf(values[column], height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
