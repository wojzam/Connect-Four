package com.connectfour.app.model.commands;

public interface Command {

    void execute();

    void undo();

}
