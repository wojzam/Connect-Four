package com.connect_four.app.ai;

import com.connect_four.app.Board;
import com.connect_four.app.Disk;

import static com.connect_four.app.Disk.EMPTY;

public class BoardEvaluator {

    public static int evaluate(Board board, Disk playerID) {
        Disk[][] values = board.getValues();
        Disk[] sequence = new Disk[4];
        int score = 0;
        int width = board.getWidth();
        int height = board.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 3; j++) {
                for (int k = 0; k < 4; k++) {
                    sequence[k] = values[i][j + k];
                }
                score += getSequenceScore(sequence, playerID);
            }
        }

        for (int i = 0; i < width - 3; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < 4; k++) {
                    sequence[k] = values[i + k][j];
                }
                score += getSequenceScore(sequence, playerID);
            }
        }

        for (int i = 0; i < width - 3; i++) {
            for (int j = 0; j < height - 3; j++) {
                for (int k = 0; k < 4; k++) {
                    sequence[k] = values[i + k][j + k];
                }
                score += getSequenceScore(sequence, playerID);
            }
        }

        for (int i = 3; i < width; i++) {
            for (int j = 0; j < height - 3; j++) {
                for (int k = 0; k < 4; k++) {
                    sequence[k] = values[i - k][j + k];
                }
                score += getSequenceScore(sequence, playerID);
            }
        }
        return score;
    }

    private static int getSequenceScore(Disk[] sequence, Disk playerID) {
        int score = 0;
        int playerDiscCount = 0;
        int emptyCount = 0;

        for (Disk value : sequence) {
            if (value == playerID) {
                playerDiscCount += 1;
            } else if (value == EMPTY) {
                emptyCount += 1;
            }
        }

        if (playerDiscCount == 4) {
            score += 100;
        } else if (playerDiscCount == 3 && emptyCount == 1) {
            score += 5;
        } else if (playerDiscCount == 2 && emptyCount == 2) {
            score += 2;
        } else if (playerDiscCount == 0 && emptyCount == 1) {
            score -= 4;
        }
        return score;
    }
}
