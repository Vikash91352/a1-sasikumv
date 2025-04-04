package ca.mcmaster.se2aa4.mazerunner;

import java.lang.String;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {

        Options options = new Options();
        options.addOption("i", true, "Reading the maze from file");
        options.addOption("p", true, "Verify User Given Path");

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse( options, args);        

        try {

            if ((commandLine.hasOption("i")) && (!(commandLine.hasOption("p")))) {

                String file = args[1];

                Player player = new Player(file, null);
                player.findPathSolution();

            }

            if ((commandLine.hasOption("i")) && (commandLine.hasOption("p"))) {

                String file = args[1];
                String pathInput = args[3];

                Player player = new Player(file, pathInput);
                System.out.println("User Given Success Path is " + player.verifyMazePath());

            }

        } catch(Exception e) {
            System.out.println("/!\\ An error has occured /!\\");
        };

    }
}