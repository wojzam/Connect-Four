package com.connectfour.app.ai;

import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;

import static com.connectfour.app.model.Disk.EMPTY;

public class BoardEvaluator extends Board {

    public static final int SEQUENCE_LENGTH = 4;
    public static final int SCORE_THREE_IN_ROW = 5;
    public static final int SCORE_TWO_IN_ROW = 2;
    public static final int SCORE_THREE_IN_ROW_OPPONENT = -4;

    public BoardEvaluator(Board copy) {
        super(copy);
    }

    private static int getSequenceScore(Disk[] sequence, Disk playerID) {
        byte playerDiskCount = 0;
        byte emptyCount = 0;

        for (Disk value : sequence) {
            if (value == playerID) {
                playerDiskCount += 1;
            } else if (value == EMPTY) {
                emptyCount += 1;
            }
        }

        if (playerDiskCount == 3 && emptyCount == 1) {
            return SCORE_THREE_IN_ROW;
        }
        if (playerDiskCount == 2 && emptyCount == 2) {
            return SCORE_TWO_IN_ROW;
        }
        if (playerDiskCount == 0 && emptyCount == 1) {
            return SCORE_THREE_IN_ROW_OPPONENT;
        }
        return 0;
    }

    public int evaluate(Disk playerID) {
        Disk[] sequence = new Disk[SEQUENCE_LENGTH];
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};
        int score = 0;
        int x, y, k;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int[] direction : directions) {
                    x = i;
                    y = j;
                    for (k = 0; k < SEQUENCE_LENGTH; k++) {
                        if (x < 0 || x >= width || y < 0 || y >= height) {
                            break;
                        }
                        sequence[k] = values[x][y];
                        x += direction[0];
                        y += direction[1];
                    }
                    if (k == SEQUENCE_LENGTH) {
                        score += getSequenceScore(sequence, playerID);
                    }
                }
            }
        }
        return score;
    }

}
