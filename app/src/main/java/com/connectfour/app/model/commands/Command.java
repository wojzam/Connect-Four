package com.connectfour.app.model.commands;

/**
 * Interface for a command in the Command design pattern.
 * Implementing classes should provide methods to execute and undo the command.
 */
public interface Command {

    /**
     * Executes the command.
     */
    void execute();

    /**
     * Undoes the command.
     */
    void undo();

}
