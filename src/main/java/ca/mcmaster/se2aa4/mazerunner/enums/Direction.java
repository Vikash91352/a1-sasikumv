package ca.mcmaster.se2aa4.mazerunner.enums;


public enum Direction {
    North,East,West,South;

    @Override
    public String toString(){
        String new_str = "";
        switch (this) {
            case North:
                new_str = "N";
                break;
            case East:
                new_str = "E";
                break;
            case West:
                new_str = "W";
                break;
            case South:
                new_str = "S";
                break;
        };

        return new_str;
    }
    public static Direction fromString(String s){
        switch (s) {
            case "North":
                return Direction.North;
            case "East":
                return Direction.East;
            case "West":
                return Direction.West;
            case "South":
                return Direction.South;
        };
        return null;
    }
}