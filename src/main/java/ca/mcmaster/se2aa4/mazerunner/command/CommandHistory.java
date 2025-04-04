package ca.mcmaster.se2aa4.mazerunner.command;
import java.util.Stack;

public class CommandHistory{

    private Stack<CommandPattern> history = new Stack<CommandPattern>();

    public void push(CommandPattern command){
        this.history.push(command);
    }

    public CommandPattern pop(){
        return this.history.pop();
    }

    public boolean isEmpty(){ return this.history.isEmpty();}

}