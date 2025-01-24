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

abstract class MazeRunnerGame {

    private int[] playerPosition;
    private String [] playerDirection;
    private String[][] mazeRunnerMap;
    private StringBuffer mazePath = new StringBuffer();

    public MazeRunnerGame(int[] playerPos, String[] playerDir, String[][] gameMap) {
        playerPosition = playerPos;
        mazeRunnerMap = gameMap;
        playerDirection = playerDir;
    }

    public int[] getPlayerPosition(){
        return playerPosition;
    }

    abstract public void turnRight();

    abstract public void turnLeft ();

    abstract public void moveForward();

    abstract public StringBuffer printMazePath();

    abstract public Boolean verifyMazePath();

}