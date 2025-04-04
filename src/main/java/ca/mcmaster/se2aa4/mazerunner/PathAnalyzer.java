package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.command.*;

import ca.mcmaster.se2aa4.mazerunner.enums.Direction;

public interface PathAnalyzer {

    public CommandPattern makeTurningDecision(Direction markerDirection, int[] markerPosition);
    public Boolean verifyNextMovement(Direction markerDirection, int[] markerPosition);
    public Boolean verifyMazePath();

}