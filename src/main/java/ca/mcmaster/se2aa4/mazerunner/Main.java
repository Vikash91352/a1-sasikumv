package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.lang.String;
import java.util.*;

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
        options.addOption("p", true, "Verify User Given Path");

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse( options, args);        

        try {

            if ((commandLine.hasOption("i")) && (!(commandLine.hasOption("p")))) {

                String file = args[1];

                MazeRunnerGame mazeRunnerObject = new MazeRunnerGamePathMachine(file);
                mazeRunnerObject.printMazePath();

            }

            if ((commandLine.hasOption("i")) && (commandLine.hasOption("p"))) {

                String file = args[1];
                String pathInput = args[3];

                MazeRunnerGame mazeRunnerObject = new MazeRunnerGamePathMachine(file);
                System.out.println("User Given Success Path is " + mazeRunnerObject.verifyMazePath(pathInput));

            }

        } catch(Exception e) {
            System.out.println("/!\\ An error has occured /!\\");
        };

    }
}