package com.connectfour.app.commands;

import java.util.Stack;

public class CommandHistory extends Stack<Command> {

    public void executeAndSave(Command command){
        command.execute();
        push(command);
    }

    public void undoLastCommand() {
        if (!isEmpty()) {
            pop().undo();
        }
    }

}
