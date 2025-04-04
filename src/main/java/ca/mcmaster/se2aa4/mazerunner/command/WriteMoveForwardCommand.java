package ca.mcmaster.se2aa4.mazerunner.command;
import ca.mcmaster.se2aa4.mazerunner.*;

public class WriteMoveForwardCommand extends CommandPattern{

    public WriteMoveForwardCommand(Player editor){
        super(editor);
    }

    @Override
    public boolean execute(){
        return true;
    }

    @Override
    public String write(){
        return "F";
    }

}