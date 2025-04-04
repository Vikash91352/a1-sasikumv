package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.enums.*;

public class PlayerMarker {

    public Direction turnRight(Direction markerDirection){

        if (markerDirection == Direction.North) {
            return Direction.East;
        }else if (markerDirection == Direction.East) {
            return Direction.South;
        }else if (markerDirection == Direction.South) {
            return Direction.West;
        }else {
            return Direction.North;
        }

    }

    public Direction turnLeft(Direction markerDirection){

        if (markerDirection == Direction.North) {
            return Direction.West;
        }else if (markerDirection == Direction.East) {
            return Direction.North;
        }else if (markerDirection == Direction.South) {
            return Direction.East;
        }else {
            return Direction.South;
        }

    }

    public int[] moveForward(Direction markerDirection, int[] markerPosition) {

        int[] newMarkerPostion = new int[2];

        if (markerDirection == Direction.North) {
            newMarkerPostion[0] = markerPosition[0] - 1;
            newMarkerPostion[1] = markerPosition[1];
        }else if (markerDirection == Direction.East) {
            newMarkerPostion[0] = markerPosition[0];
            newMarkerPostion[1] = markerPosition[1] + 1;
        }else if (markerDirection == Direction.South) {
            newMarkerPostion[0] = markerPosition[0] + 1;
            newMarkerPostion[1] = markerPosition[1] ;
        }else {
            newMarkerPostion[0] = markerPosition[0] ;
            newMarkerPostion[1] = markerPosition[1] - 1;
        }

        return newMarkerPostion;
    }
}