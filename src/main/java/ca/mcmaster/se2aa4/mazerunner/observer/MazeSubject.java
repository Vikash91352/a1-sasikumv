package ca.mcmaster.se2aa4.mazerunner.observer;

import java.util.ArrayList;
public abstract class MazeSubject {

    protected ArrayList<ObservingMazePlayer> players = new ArrayList<ObservingMazePlayer>();

    public void attach(ObservingMazePlayer observer){
        this.players.add(observer);
    }

    public void deattach(ObservingMazePlayer observer){
        this.players.remove(observer);
    }

    public abstract void notifyAllObservers();
}