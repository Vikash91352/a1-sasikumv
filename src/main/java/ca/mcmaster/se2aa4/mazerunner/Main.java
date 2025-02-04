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

abstract class MazeRunnerGame {

    protected int[] markerPosition = new int[2];

    protected StringBuffer markerDirection = new StringBuffer("East");

    protected String[][] mazeRunnerMap;
    protected StringBuffer mazePath = new StringBuffer();

    public MazeRunnerGame(String[][] gameMap) {
        mazeRunnerMap = gameMap;
    }

    public int[] getMarkerPosition(){
        return markerPosition;
    }

    public StringBuffer getMarkerDirection(){
        return markerDirection;
    }

    abstract protected int[] findWestSideEntrance();

    abstract protected void turnRight();

    abstract protected void turnLeft();

    abstract protected Boolean verifyNextMovement();

    abstract protected void moveForward();

    abstract public void printMazePath();

    abstract public StringBuffer convertCanonicalToFactorized(String canonicalPathForm);

    abstract public Boolean verifyMazePath(String userInputedPath);

}

class MazeRunnerGamePathMachine extends MazeRunnerGame {

    public MazeRunnerGamePathMachine(String[][] gameMap) {
        super(gameMap);

        markerPosition[0] = findWestSideEntrance()[0];
        markerPosition[1] = findWestSideEntrance()[1];

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

        int markerDirectionLength = markerDirection.length();
        String stringMarkerDirection = markerDirection.toString();
        markerDirection.delete(0,markerDirectionLength);

        if (stringMarkerDirection.equals("North")) {

            markerDirection.append("East");

        }else if (stringMarkerDirection.equals("East")) {

            markerDirection.append("South");

        }else if (stringMarkerDirection.equals("South")) {

            markerDirection.append("West");

        }else if (stringMarkerDirection.equals("West")) {

            markerDirection.append("North");

        }

    }

    protected void turnLeft(){

        int markerDirectionLength = markerDirection.length();
        String stringMarkerDirection = markerDirection.toString();
        markerDirection.delete(0,markerDirectionLength);

        if (stringMarkerDirection.equals("North")) {

            markerDirection.append("West");

        }else if (stringMarkerDirection.equals("East")) {

            markerDirection.append("North");

        }else if (stringMarkerDirection.equals("South")) {

            markerDirection.append("East");

        }else if (stringMarkerDirection.equals("West")) {

            markerDirection.append("South");

        }

    }

    protected Boolean verifyNextMovement(){

        int[] currentMarkerPosition = new int[2];
        currentMarkerPosition[0] = markerPosition[0];
        currentMarkerPosition[1] = markerPosition[1];

        String stringMarkerDirection = markerDirection.toString();

        if (stringMarkerDirection.equals("North")) {

            currentMarkerPosition[0] -= 1;

        }else if (stringMarkerDirection.equals("East")) {

            currentMarkerPosition[1] += 1;

        }else if (stringMarkerDirection.equals("South")) {

            currentMarkerPosition[0] += 1;

        }else if (stringMarkerDirection.equals("West")) {

            currentMarkerPosition[1] -= 1;

        }

        if (currentMarkerPosition[1] > (mazeRunnerMap[0].length - 1)) {

            return false;
        }
        
        if (mazeRunnerMap[currentMarkerPosition[0]][currentMarkerPosition[1]].equals(" ")) {
            return true;
        } else {
            return false;
        }

    }

    protected void moveForward(){

        String stringMarkerDirection = markerDirection.toString();

        if (stringMarkerDirection.equals("North")) {

            markerPosition[0] -= 1;

        }else if (stringMarkerDirection.equals("East")) {

            markerPosition[1] += 1;

        }else if (stringMarkerDirection.equals("South")) {

            markerPosition[0] += 1;

        }else if (stringMarkerDirection.equals("West")) {

            markerPosition[1] -= 1;

        }

    }

    public String chooseToTurn() {

        int[] currentMarkerPosition = new int[2];
        currentMarkerPosition[0] = markerPosition[0];
        currentMarkerPosition[1] = markerPosition[1];

        String stringMarkerDirection = markerDirection.toString();

        if (stringMarkerDirection.equals("North")) {

            if (mazeRunnerMap[currentMarkerPosition[0]][currentMarkerPosition[1] + 1].equals(" ")) { // Checking if there is an empty space to the right
                turnRight();
                return "R";
            } else if (mazeRunnerMap[currentMarkerPosition[0] - 1][currentMarkerPosition[1]].equals("#")) { // Checking if the area infront is blocked
                turnLeft();
                return "L";
            }

        }else if (stringMarkerDirection.equals("East")) {

            if (mazeRunnerMap[currentMarkerPosition[0] + 1][currentMarkerPosition[1]].equals(" ")) { // Checking if there is an empty space to the right
                turnRight();
                return "R";
            } else if (mazeRunnerMap[currentMarkerPosition[0]][currentMarkerPosition[1] + 1].equals("#")) { // Checking if the area infront is blocked
                turnLeft();
                return "L";
            }

        }else if (stringMarkerDirection.equals("South")) {

            if (mazeRunnerMap[currentMarkerPosition[0]][currentMarkerPosition[1] - 1].equals(" ")) { // Checking if there is an empty space to the right
                turnRight();
                return "R";
            } else if (mazeRunnerMap[currentMarkerPosition[0] + 1][currentMarkerPosition[1]].equals("#")) { // Checking if the area infront is blocked
                turnLeft();
                return "L";
            }

        }else if (stringMarkerDirection.equals("West")) {

            if (mazeRunnerMap[currentMarkerPosition[0] - 1][currentMarkerPosition[1]].equals(" ")) { // Checking if there is an empty space to the right
                turnRight();
                return "R";
            } else if (mazeRunnerMap[currentMarkerPosition[0]][currentMarkerPosition[1] - 1].equals("#")) { // Checking if the area infront is blocked
                turnLeft();
                return "L";
            }

        }

        return "";

    }

    public void printMazePath(){

        StringBuffer canonicalPath = new StringBuffer();

        while (true) {

            canonicalPath.append(chooseToTurn());
            if (verifyNextMovement()) {
                moveForward();
                canonicalPath.append("F");

                if (markerPosition[1] == (mazeRunnerMap[0].length - 1)) {

                    String factorizedPath = convertCanonicalToFactorized(canonicalPath.toString()).toString();

                    System.out.println("The Canonical Path is " + factorizedPath.toString());

                    break;
                }

            }

        }

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

    public Boolean verifyMazePath(String userInputedPath){

        for (int i = 0; i < userInputedPath.length(); i++) {

            if (userInputedPath.charAt(i) == 'F') {

                if (verifyNextMovement()) {

                    moveForward();
                    
                } else {

                    return false;

                }

            } else if (userInputedPath.charAt(i) == 'R') {

                turnRight();

            } else if (userInputedPath.charAt(i) == 'L') {

                turnLeft();

            } else {

                return false;

            }

        }

        if (markerPosition[1] == (mazeRunnerMap[0].length - 1)) {
            return true;
        } else {
            return false;
        }
    }


}

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

                BufferedReader reader = new BufferedReader(new FileReader(file));

                String line;

                ArrayList<ArrayList<String>> mazeMapArrayList = new ArrayList<ArrayList<String>>();
                
                int rowCount = 0;
                while ((line = reader.readLine()) != null) {

                    mazeMapArrayList.add(new ArrayList<String>(line.length()));

                    for (int idx = 0; idx < line.length(); idx++) {
                        char characterInLine = line.charAt(idx);
                        String singleStringInLine = Character.toString(characterInLine);

                        mazeMapArrayList.get(rowCount).add(idx, singleStringInLine);
                    }
                    rowCount += 1;
                }


                String[][] mazeMapArray = new String[mazeMapArrayList.size()][];

                //Converting 2d ArrayList to 2d Array
                for (int i = 0; i < mazeMapArrayList.size(); i++) {
                    ArrayList<String> mazeRowArrayList = mazeMapArrayList.get(i);

                    String[] mazeRowArray = new String[mazeMapArrayList.get(0).size()];

                    for (int j = 0; j < mazeRowArrayList.size(); j++) {
                        mazeRowArray[j] = mazeRowArrayList.get(j);
                    }

                    if (mazeRowArrayList.size() < mazeMapArrayList.get(0).size()) {
                        for (int j = mazeRowArrayList.size(); j < mazeMapArrayList.get(1).size(); j++) {
                            mazeRowArray[j] = " ";
                        }     
                    }

                    mazeMapArray[i] = mazeRowArray;
                }

                MazeRunnerGame mazeRunnerObject = new MazeRunnerGamePathMachine(mazeMapArray);
                mazeRunnerObject.printMazePath();

            }

            if ((commandLine.hasOption("i")) && (commandLine.hasOption("p"))) {

                String file = args[1];
                String pathInput = args[3];

                BufferedReader reader = new BufferedReader(new FileReader(file));

                String line;

                ArrayList<ArrayList<String>> mazeMapArrayList = new ArrayList<ArrayList<String>>();
                
                int rowCount = 0;
                while ((line = reader.readLine()) != null) {

                    mazeMapArrayList.add(new ArrayList<String>(line.length()));

                    for (int idx = 0; idx < line.length(); idx++) {
                        char characterInLine = line.charAt(idx);
                        String singleStringInLine = Character.toString(characterInLine);

                        mazeMapArrayList.get(rowCount).add(idx, singleStringInLine);

                    }
                    rowCount += 1;
                }


                String[][] mazeMapArray = new String[mazeMapArrayList.size()][];

                //Converting 2d ArrayList to 2d Array
                for (int i = 0; i < mazeMapArrayList.size(); i++) {
                    ArrayList<String> mazeRowArrayList = mazeMapArrayList.get(i);

                    String[] mazeRowArray = new String[mazeMapArrayList.get(0).size()];

                    for (int j = 0; j < mazeRowArrayList.size(); j++) {
                        mazeRowArray[j] = mazeRowArrayList.get(j);
                    }

                    if (mazeRowArrayList.size() < mazeMapArrayList.get(0).size()) {
                        for (int j = mazeRowArrayList.size(); j < mazeMapArrayList.get(1).size(); j++) {
                            mazeRowArray[j] = " ";
                        }     
                    }

                    mazeMapArray[i] = mazeRowArray;
                }

                MazeRunnerGame mazeRunnerObject = new MazeRunnerGamePathMachine(mazeMapArray);
                System.out.println("User Given Success Path is " + mazeRunnerObject.verifyMazePath(pathInput));

            }

        } catch(Exception e) {
            System.out.println("/!\\ An error has occured /!\\");
        };

    }
}