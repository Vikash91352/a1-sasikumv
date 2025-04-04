package ca.mcmaster.se2aa4.mazerunner.enums;


public enum PathBlock {
    WALL, PASS;

    @Override
    public String toString(){
        String new_str = "";
        switch (this) {
            case WALL:
                new_str = "#";
                break;
            case PASS:
                new_str = " ";
                break;
        };

        return new_str;
    }
    public static PathBlock fromString(String s){
        switch (s) {
            case "#":
                return PathBlock.WALL;
            case " ":
                return PathBlock.PASS;
        };
        return null;
    }
}