package ca.mcmaster.se2aa4.mazerunner.observer;

import ca.mcmaster.se2aa4.mazerunner.enums.PathBlock;

public interface ObservingMazePlayer {
    public void update(PathBlock[][] mazeArray);
}
