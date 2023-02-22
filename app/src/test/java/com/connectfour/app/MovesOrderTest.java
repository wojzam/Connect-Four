package com.connectfour.app;

import com.connectfour.app.ai.MovesOrder;
import com.connectfour.app.model.Board;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MovesOrderTest {

    @Test
    void shouldOrderRandomly() {
        Board board = new Board(100, 1);

        List<Integer> order1 = MovesOrder.getAvailableColumns(board, MovesOrder.RANDOM_ORDER_THRESHOLD);
        List<Integer> order2 = MovesOrder.getAvailableColumns(board, MovesOrder.RANDOM_ORDER_THRESHOLD);

        assertNotEquals(order1, order2);
    }

    @Test
    void shouldOrderByDistanceToCenter() {
        Board board = new Board(7, 1);

        List<Integer> expectedOrder = Arrays.asList(3, 2, 4, 1, 5, 0, 6);
        List<Integer> actualOrder = MovesOrder.getAvailableColumns(board, MovesOrder.RANDOM_ORDER_THRESHOLD + 1);
        assertEquals(expectedOrder, actualOrder);
    }

}
