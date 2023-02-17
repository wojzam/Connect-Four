package com.connectfour.app.model.commands;

import java.util.Stack;

public class CommandHistory extends Stack<Command> {

    public void undoLastCommand() {
        if (!isEmpty()) {
            pop().undo();
        }
    }

}
