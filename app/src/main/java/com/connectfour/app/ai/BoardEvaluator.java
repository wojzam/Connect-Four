package com.connectfour.app.ai;

import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;

import static com.connectfour.app.model.Disk.EMPTY;

/**
 * The {@link BoardEvaluator} class extends the {@link Board} class and is used by the {@link AI} player
 * to evaluate "goodness" of the current state of board, in the context of the MinMax algorithm.
 */
public class BoardEvaluator extends Board {

    /**
     * The length of sequences to evaluate
     */
    public static final int SEQUENCE_LENGTH = 4;

    /**
     * The score assigned to a sequence of three player disks in a row with one empty space
     */
    public static final int SCORE_THREE_IN_ROW = 5;

    /**
     * The score assigned to a sequence of two player disks in a row with two empty spaces
     */
    public static final int SCORE_TWO_IN_ROW = 2;

    /**
     * The score assigned to a sequence of three opponent disks in a row with one empty space
     */
    public static final int SCORE_THREE_IN_ROW_OPPONENT = -4;

    /**
     * Creates a new {@link BoardEvaluator} object based on a copy of the given {@link Board} object.
     *
     * @param copy the Board object to create a copy from
     */
    public BoardEvaluator(Board copy) {
        super(copy);
    }

    /**
     * Computes the score for a given sequence of {@link Disk} objects for the given player Disk.
     *
     * @param sequence   the evaluated sequence of Disk objects
     * @param playerDisk the Disk of the player used to compute the sequence score
     * @return the total score for a sequence
     */
    private static int getSequenceScore(Disk[] sequence, Disk playerDisk) {
        byte playerDiskCount = 0;
        byte emptyCount = 0;

        for (Disk value : sequence) {
            if (value == playerDisk) {
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

    /**
     * Evaluates the current state of the board for a given player {@link Disk} by scoring the sequences of disks
     * on the board in all directions and summing up the scores.
     *
     * @param playerDisk the Disk of the player to evaluate the board state for
     * @return the total score for the given player based on the current state of the game board
     */
    public int evaluateFor(Disk playerDisk) {
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
                        score += getSequenceScore(sequence, playerDisk);
                    }
                }
            }
        }
        return score;
    }

}
