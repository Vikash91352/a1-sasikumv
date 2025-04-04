package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.enums.*;
import ca.mcmaster.se2aa4.mazerunner.observer.*;

import ca.mcmaster.se2aa4.mazerunner.command.*;

public class Player implements ObservingMazePlayer, PathAnalyzer {

    private Direction playerMarkerDirection;
    private int[] playerMarkerPosition = new int[2];
    private PathBlock[][] mazeArray;

    private MazeGame maze;
    private PlayerMarker marker;

    private StringBuffer pathInstructionStringBuffer;
    private CommandHistory history = new CommandHistory();

    public Player(String mapFileString, String pathInstructionString){

        playerMarkerDirection = Direction.East;

        maze = new MazeGame(mapFileString);
        maze.attach(this);
        maze.setState(0);

        this.playerMarkerPosition[0] = maze.findWestSideEntrance(mapFileString)[0];
        this.playerMarkerPosition[1] = maze.findWestSideEntrance(mapFileString)[1];

        marker = new PlayerMarker();

        if (pathInstructionString != null) {
            pathInstructionStringBuffer = new StringBuffer(pathInstructionString);
        }

    }

    public int[] getMarkerPosition() {

        int[] currentMarkerPosition = new int[2];
        currentMarkerPosition[0] = playerMarkerPosition[0];
        currentMarkerPosition[1] = playerMarkerPosition[1];

        return currentMarkerPosition;

    }


    public void update(PathBlock[][] mazeArray){
        if (maze.getState() == 0) {
            this.mazeArray = new PathBlock[mazeArray.length][mazeArray[0].length];
            this.mazeArray = mazeArray;
        } 
    }

    public CommandPattern makeTurningDecision(Direction markerDirection, int[] markerPosition){
        
        int[] currentMarkerPosition = new int[2];
        currentMarkerPosition[0] = markerPosition[0];
        currentMarkerPosition[1] = markerPosition[1];

        if (markerDirection == Direction.North) {

            if (mazeArray[currentMarkerPosition[0]][currentMarkerPosition[1] + 1] == PathBlock.PASS) { // Checking if there is an empty space to the right
                playerMarkerDirection = marker.turnRight(markerDirection);
                return new WriteTurnRightCommand(this);
            } else if (mazeArray[currentMarkerPosition[0] - 1][currentMarkerPosition[1]] == PathBlock.WALL) { // Checking if the area infront is blocked
                playerMarkerDirection = marker.turnLeft(markerDirection);
                return new WriteTurnLeftCommand(this);
            }

        }else if (markerDirection == Direction.East) {

            if (mazeArray[currentMarkerPosition[0] + 1][currentMarkerPosition[1]] == PathBlock.PASS) { // Checking if there is an empty space to the right
                playerMarkerDirection = marker.turnRight(markerDirection);
                return new WriteTurnRightCommand(this);
            } else if (mazeArray[currentMarkerPosition[0]][currentMarkerPosition[1] + 1] == PathBlock.WALL) { // Checking if the area infront is blocked
                playerMarkerDirection = marker.turnLeft(markerDirection);
                return new WriteTurnLeftCommand(this);
            }

        }else if (markerDirection == Direction.South) {

            if (mazeArray[currentMarkerPosition[0]][currentMarkerPosition[1] - 1] == PathBlock.PASS) { // Checking if there is an empty space to the right
                playerMarkerDirection = marker.turnRight(markerDirection);
                return new WriteTurnRightCommand(this);
            } else if (mazeArray[currentMarkerPosition[0] + 1][currentMarkerPosition[1]] == PathBlock.WALL) { // Checking if the area infront is blocked
                playerMarkerDirection = marker.turnLeft(markerDirection);
                return new WriteTurnLeftCommand(this);
            }

        }else if (markerDirection == Direction.West) {

            if (mazeArray[currentMarkerPosition[0] - 1][currentMarkerPosition[1]] == PathBlock.PASS) { // Checking if there is an empty space to the right
                playerMarkerDirection = marker.turnRight(markerDirection);
                return new WriteTurnRightCommand(this);
            } else if (mazeArray[currentMarkerPosition[0]][currentMarkerPosition[1] - 1] == PathBlock.WALL) { // Checking if the area infront is blocked
                playerMarkerDirection = marker.turnLeft(markerDirection);
                return new WriteTurnLeftCommand(this);
            }

        }

        return new WriteNoTurnCommand(this);

    }
    public Boolean verifyNextMovement(Direction markerDirection, int[] markerPosition) {

        int[] currentMarkerPosition = new int[2];
        currentMarkerPosition[0] = markerPosition[0];
        currentMarkerPosition[1] = markerPosition[1];

        if (markerDirection == Direction.North) {

            currentMarkerPosition[0] -= 1;

        }else if (markerDirection == Direction.East) {

            currentMarkerPosition[1] += 1;

        }else if (markerDirection == Direction.South) {

            currentMarkerPosition[0] += 1;

        }else if (markerDirection == Direction.West) {

            currentMarkerPosition[1] -= 1;

        }

        if (currentMarkerPosition[1] > (mazeArray[0].length - 1)) {

            return false;
        }
        
        if (mazeArray[currentMarkerPosition[0]][currentMarkerPosition[1]] == PathBlock.PASS) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean verifyMazePath(){

        for (int i = 0; i < pathInstructionStringBuffer.length(); i++) {

            if (pathInstructionStringBuffer.charAt(i) == 'F') {

                if (this.verifyNextMovement(playerMarkerDirection, playerMarkerPosition)) {

                    playerMarkerPosition = marker.moveForward(playerMarkerDirection, playerMarkerPosition);
                    
                } else {

                    return false;

                }

            } else if (pathInstructionStringBuffer.charAt(i) == 'R') {

                playerMarkerDirection = marker.turnRight(playerMarkerDirection);

            } else if (pathInstructionStringBuffer.charAt(i) == 'L') {

                playerMarkerDirection = marker.turnLeft(playerMarkerDirection);

            } else {

                return false;

            }

        }

        if (playerMarkerPosition[1] == (mazeArray[0].length - 1)) {
            return true;
        } else {
            return false;
        }


    }

    public void findPathSolution() {


        while (true) {
            this.executeCommand(this.makeTurningDecision(playerMarkerDirection, playerMarkerPosition));
            if (this.verifyNextMovement(playerMarkerDirection, playerMarkerPosition)) {

                playerMarkerPosition = marker.moveForward(playerMarkerDirection, playerMarkerPosition);
                this.executeCommand(new WriteMoveForwardCommand(this));

                if (playerMarkerPosition[1] == (mazeArray[0].length - 1)) {

                    break;
                }

            }

        }

        StringBuffer canonicalSolutionPath = new StringBuffer();
        PathInstructionActions pathInstructionActions = new PathInstructionActions();


        while (true) {

            if (this.history.isEmpty()) {
                break;
            } else {
                CommandPattern command = history.pop();
                canonicalSolutionPath.append(command.write());     
            }

        }

        String factorizedPath = pathInstructionActions.convertToFactorizedPath(canonicalSolutionPath.reverse().toString());
        pathInstructionActions.printPath(factorizedPath);

    }


    private void executeCommand(CommandPattern command){
        if (command.execute()) {
            this.history.push(command);
        }
    }

}