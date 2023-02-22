package com.connectfour.app;

import com.connectfour.app.model.Disk;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.connectfour.app.model.Disk.EMPTY;
import static com.connectfour.app.model.Disk.PLAYER_1;
import static com.connectfour.app.model.Disk.PLAYER_2;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * The {@code BoardTestData} class provides test data for a Connect Four board game.
 * The board size is 4x4 instead of the standard size of 7x6 for simplification purposes.
 * The boards arrays contain sequences of three instead of four because it provides
 * a more flexible and reusable approach. Having an almost finished winning condition in the board
 * allows it to be used, for example, in AI player tests to check if the AI selects the missing column.
 * If it is necessary to test if winning sequences are identified correctly,
 * just one disk needs to be added to complete the sequence.
 *
 * @see BoardUnitTest
 * @see AIUnitTest
 */
public class BoardTestData {

    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;

    public static final Disk[][] EMPTY_BOARD = {
            {EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY}};

    public static final Disk[][] FULL_BOARD = {
            {PLAYER_1, PLAYER_1, PLAYER_2, PLAYER_2},
            {PLAYER_2, PLAYER_2, PLAYER_1, PLAYER_1},
            {PLAYER_1, PLAYER_1, PLAYER_2, PLAYER_2},
            {PLAYER_2, PLAYER_2, PLAYER_1, PLAYER_1}};

    public static final Disk[][] THREE_IN_ROW_HORIZONTAL = {
            {PLAYER_1, EMPTY, EMPTY, EMPTY},
            {PLAYER_1, EMPTY, EMPTY, EMPTY},
            {PLAYER_1, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY}};

    public static final Disk[][] THREE_IN_ROW_VERTICAL = {
            {PLAYER_1, PLAYER_1, PLAYER_1, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY}};

    public static final Disk[][] THREE_IN_ROW_DIAGONAL_POSITIVE_SLOPE = {
            {PLAYER_1, EMPTY, EMPTY, EMPTY},
            {PLAYER_2, PLAYER_1, EMPTY, EMPTY},
            {PLAYER_1, PLAYER_2, PLAYER_1, EMPTY},
            {PLAYER_2, PLAYER_1, PLAYER_2, EMPTY}};

    public static final Disk[][] THREE_IN_ROW_DIAGONAL_NEGATIVE_SLOPE = {
            {PLAYER_2, PLAYER_1, PLAYER_2, EMPTY},
            {PLAYER_1, PLAYER_2, PLAYER_1, EMPTY},
            {PLAYER_2, PLAYER_1, EMPTY, EMPTY},
            {PLAYER_1, EMPTY, EMPTY, EMPTY}};

    /**
     * Provides test cases for a board with a potential winning sequence.
     * Each test case consists of a 2D array representing the board an an integer
     * representing the column where a move can be made to complete the winning sequence.
     * The third argument is a string describing each sequence for better test reporting.
     *
     * @return a stream of Arguments objects representing cases for the board with a potential winning sequence
     */
    public static Stream<Arguments> providePotentialWinningSequences() {
        return Stream.of(
                arguments(THREE_IN_ROW_HORIZONTAL, 3, "horizontal sequence"),
                arguments(THREE_IN_ROW_VERTICAL, 0, "vertical sequence"),
                arguments(THREE_IN_ROW_DIAGONAL_POSITIVE_SLOPE, 3, "positive slope diagonal sequence"),
                arguments(THREE_IN_ROW_DIAGONAL_NEGATIVE_SLOPE, 0, "negative slope diagonal sequence")
        );
    }

    /**
     * Provides test cases for a board without a winning sequence.
     * Each test case consists of a 2D array representing the board.
     * The second argument is a string describing each board state for better test reporting.
     *
     * @return a stream of Arguments objects representing cases for the board without a winning sequence
     */
    public static Stream<Arguments> provideNonWinningSequences() {
        return Stream.of(
                arguments(EMPTY_BOARD, "empty board"),
                arguments(FULL_BOARD, "full board with tie"),
                arguments(THREE_IN_ROW_HORIZONTAL, "too short horizontal sequence"),
                arguments(THREE_IN_ROW_VERTICAL, "too short vertical sequence"),
                arguments(THREE_IN_ROW_DIAGONAL_POSITIVE_SLOPE, "too short positive slope diagonal sequence"),
                arguments(THREE_IN_ROW_DIAGONAL_NEGATIVE_SLOPE, "too short negative slope diagonal sequence")
        );
    }
}
