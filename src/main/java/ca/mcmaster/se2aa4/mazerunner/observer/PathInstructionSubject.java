package ca.mcmaster.se2aa4.mazerunner.observer;

import java.util.ArrayList;
public abstract class PathInstructionSubject {

    protected ArrayList<PathInstructionObserver> players = new ArrayList<PathInstructionObserver>();

    public void attach(PathInstructionObserver observer){
        this.players.add(observer);
    }

    public void deattach(PathInstructionObserver observer){
        this.players.remove(observer);
    }

    public abstract void notifyAllObservers();
}