package ca.mcmaster.se2aa4.mazerunner.command;

import ca.mcmaster.se2aa4.mazerunner.*;

public abstract class CommandPattern{

    public Player editor;
    
    public CommandPattern(Player editor){
        this.editor = editor;
    }

    public abstract boolean execute();

    public abstract String write();

}