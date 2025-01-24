package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

abstract class MazeRunnerGame {

    protected int[] markerPosition;
    protected String markerDirection = "East";
    protected String[][] mazeRunnerMap;
    protected StringBuffer mazePath = new StringBuffer();

    public MazeRunnerGame(String[][] gameMap) {
        mazeRunnerMap = gameMap;
    }

    public int[] getMarkerPosition(){
        return markerPosition;
    }

    public String getMarkerDirection(){
        return markerDirection;
    }

    abstract protected int[] findWestSideEntrance();

    abstract protected void turnRight();

    abstract protected void turnLeft();

    abstract protected void moveForward();

    abstract public void printMazePath();

    abstract public StringBuffer convertCanonicalToFactorized(String canonicalPathForm);

    abstract public Boolean verifyMazePath();

}

class MazeRunnerGamePathMachine extends MazeRunnerGame {

    public MazeRunnerGamePathMachine(String[][] gameMap) {
        super(gameMap);
    }

    protected int[] findWestSideEntrance(){

        int[] initialPosition = new int[2];

        for (int i = 0; i < mazeRunnerMap.length; i++) {

            if (mazeRunnerMap[i][0].equals(" ")) {

                initialPosition[0] = i;
                initialPosition[1] = 0;

            }

        }

        return initialPosition;

    }

    protected void turnRight(){

        System.out.println("Move Right");

    }

    protected void turnLeft(){

        System.out.println("Move Left");

    }

    protected void moveForward(){

        System.out.println("Move Forward");

    }

    public void printMazePath(){

        System.out.println("Print Maze Path");

    }

    public StringBuffer convertCanonicalToFactorized(String canonicalPathForm){

        StringBuffer factorizedPathForm = new StringBuffer();

        char currentChar = ' ';
        int currentCharCount = 0;
        for (int i = 0; i < canonicalPathForm.length(); i++) {
 
            if (i == 0) {
                currentChar = canonicalPathForm.charAt(i);
                currentCharCount = 1;

            }else if (currentChar == canonicalPathForm.charAt(i)) {
                currentCharCount += 1;

            }else if (!(currentChar == canonicalPathForm.charAt(i))) {
                factorizedPathForm.append(Integer.toString(currentCharCount));
                factorizedPathForm.append(Character.toString(currentChar));

                currentChar = canonicalPathForm.charAt(i);
                currentCharCount = 1;

            }
            if (i == (canonicalPathForm.length() - 1)) {
                factorizedPathForm.append(Integer.toString(currentCharCount));
                factorizedPathForm.append(Character.toString(currentChar));

            }

        }

        return factorizedPathForm;

    }

    public Boolean verifyMazePath(){

        return true;
    }


}

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws ParseException {

        Options options = new Options();
        options.addOption("i", true, "Reading the maze from file");

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse( options, args);        

        logger.info("** Starting Maze Runner");
        try {

            if (commandLine.hasOption("i")) {

                String file = args[1];

                logger.info("**** Reading the maze from file " + file);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    for (int idx = 0; idx < line.length(); idx++) {
                        if (line.charAt(idx) == '#') {
                            logger.trace("WALL ");
                        } else if (line.charAt(idx) == ' ') {
                            logger.trace("PASS ");
                        }
                    }
                    logger.trace(System.lineSeparator());
                }

            }

            if (commandLine.hasOption("p")) {

                String pathInput = args[3];
                logger.info("Path Verified ");

            }

        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");

    }
}