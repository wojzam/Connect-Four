package com.connect_four.app;

import java.util.Arrays;

public class Board {

    public static final int EMPTY = 0;
    private final int[][] values;
    private final int width;
    private final int height;

    public Board() {
        this(7, 6);
    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        values = new int[height][width];
        clearBoard();
    }

    public void clearBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                values[i][j] = EMPTY;
            }
        }
    }

    public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(values[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean insertIntoColumn(int column, int value) {
        int index = findFirstEmptyIndex(column);
        if (index >= 0) {
            values[index][column] = value;
            return true;
        }
        return false;
    }

    public int[][] getValues() {
        int[][] valuesCopy = new int[height][width];
        for (int i = 0; i < height; i++) {
            valuesCopy[i] = Arrays.copyOf(values[i], width);
        }
        return valuesCopy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int findFirstEmptyIndex(int column) {
        for (int i = height - 1; i >= 0; i--) {
            if (values[i][column] == EMPTY) {
                return i;
            }
        }
        return -1;
    }
}
