package com.connectfour.app.model.commands;

import java.util.Stack;

/**
 * The {@code CommandHistory} is a stack used to keep track of all the commands that have been executed,
 * and allows the most recent command to be undone.
 */
public class CommandHistory extends Stack<Command> {

    /**
     * Undoes the last command on the stack by calling its undo method. If the stack is empty, does nothing.
     */
    public void undoLastCommand() {
        if (!isEmpty()) {
            pop().undo();
        }
    }

}
