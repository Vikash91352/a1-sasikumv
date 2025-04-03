package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.lang.String;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class MazeRunnerGame {

    protected int[] markerPosition = new int[2];

    protected StringBuffer markerDirection = new StringBuffer("East");

    protected String[][] mazeRunnerMap;
    protected StringBuffer mazePath = new StringBuffer();

    public MazeRunnerGame(String gameMapFilep) {
        mazeRunnerMap = createMapArray(gameMapFilep);
    }

    public int[] getMarkerPosition(){
        return markerPosition;
    }

    public StringBuffer getMarkerDirection(){
        return markerDirection;
    }

    abstract public int[] findWestSideEntrance();

    abstract public String[][] createMapArray(String file);

    abstract public String turnRight();

    abstract public String turnLeft();

    abstract public Boolean verifyNextMovement();

    abstract public int[] moveForward();

    abstract public void printMazePath();

    abstract public String convertCanonicalToFactorized(String canonicalPathForm);

    abstract public Boolean verifyMazePath(String userInputedPath);

}

public class MazeRunnerGamePathMachine extends MazeRunnerGame {

    public MazeRunnerGamePathMachine(String gameMapFile) {
        super(gameMapFile);

        markerPosition[0] = findWestSideEntrance()[0];
        markerPosition[1] = findWestSideEntrance()[1];

    }


    public String[][] createMapArray(String file) {

        try {

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
    
            return mazeMapArray;
    

        } catch(Exception e) {
            System.out.println("/!\\ An error has occured /!\\");

            return null;
        }
    }

    public int[] findWestSideEntrance(){

        int[] initialPosition = new int[2];

        for (int i = 0; i < mazeRunnerMap.length; i++) {

            if (mazeRunnerMap[i][0].equals(" ")) {

                initialPosition[0] = i;
                initialPosition[1] = 0;

            }

        }

        return initialPosition;

    }

    public String turnRight(){

        int markerDirectionLength = markerDirection.length();
        String stringMarkerDirection = markerDirection.toString();
        markerDirection.delete(0,markerDirectionLength);

        if (stringMarkerDirection.equals("North")) {

            return "East";

        }else if (stringMarkerDirection.equals("East")) {

            return "South";

        }else if (stringMarkerDirection.equals("South")) {

            return "West";

        }else {

            return "North";

        }

    }

    public String turnLeft(){

        int markerDirectionLength = markerDirection.length();
        String stringMarkerDirection = markerDirection.toString();
        markerDirection.delete(0,markerDirectionLength);

        if (stringMarkerDirection.equals("North")) {

            return "West";

        }else if (stringMarkerDirection.equals("East")) {

            return "North";

        }else if (stringMarkerDirection.equals("South")) {

            return "East";

        }else {

            return "South";

        }

    }

    public Boolean verifyNextMovement(){

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

    public int[] moveForward(){

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
        return markerPosition;

    }

    public String chooseToTurn() {

        int[] currentMarkerPosition = new int[2];
        currentMarkerPosition[0] = markerPosition[0];
        currentMarkerPosition[1] = markerPosition[1];

        String stringMarkerDirection = markerDirection.toString();

        if (stringMarkerDirection.equals("North")) {

            if (mazeRunnerMap[currentMarkerPosition[0]][currentMarkerPosition[1] + 1].equals(" ")) { // Checking if there is an empty space to the right
                markerDirection.append(turnRight());
                return "R";
            } else if (mazeRunnerMap[currentMarkerPosition[0] - 1][currentMarkerPosition[1]].equals("#")) { // Checking if the area infront is blocked
                markerDirection.append(turnLeft());
                return "L";
            }

        }else if (stringMarkerDirection.equals("East")) {

            if (mazeRunnerMap[currentMarkerPosition[0] + 1][currentMarkerPosition[1]].equals(" ")) { // Checking if there is an empty space to the right
                markerDirection.append(turnRight());
                return "R";
            } else if (mazeRunnerMap[currentMarkerPosition[0]][currentMarkerPosition[1] + 1].equals("#")) { // Checking if the area infront is blocked
                markerDirection.append(turnLeft());
                return "L";
            }

        }else if (stringMarkerDirection.equals("South")) {

            if (mazeRunnerMap[currentMarkerPosition[0]][currentMarkerPosition[1] - 1].equals(" ")) { // Checking if there is an empty space to the right
                markerDirection.append(turnRight());
                return "R";
            } else if (mazeRunnerMap[currentMarkerPosition[0] + 1][currentMarkerPosition[1]].equals("#")) { // Checking if the area infront is blocked
                markerDirection.append(turnLeft());
                return "L";
            }

        }else if (stringMarkerDirection.equals("West")) {

            if (mazeRunnerMap[currentMarkerPosition[0] - 1][currentMarkerPosition[1]].equals(" ")) { // Checking if there is an empty space to the right
                markerDirection.append(turnRight());
                return "R";
            } else if (mazeRunnerMap[currentMarkerPosition[0]][currentMarkerPosition[1] - 1].equals("#")) { // Checking if the area infront is blocked
                markerDirection.append(turnLeft());
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
                int[] newMarkerPosition = moveForward();
                canonicalPath.append("F");

                if (markerPosition[1] == (mazeRunnerMap[0].length - 1)) {

                    String factorizedPath = convertCanonicalToFactorized(canonicalPath.toString());

                    System.out.println("The Canonical Path is " + factorizedPath.toString());

                    break;
                }

            }

        }

    }

    public String convertCanonicalToFactorized(String canonicalPathForm){

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

        return factorizedPathForm.toString();

    }

    public Boolean verifyMazePath(String userInputedPath){

        for (int i = 0; i < userInputedPath.length(); i++) {

            if (userInputedPath.charAt(i) == 'F') {

                if (verifyNextMovement()) {

                    int[] newMarkerPosition = moveForward();
                    
                } else {

                    return false;

                }

            } else if (userInputedPath.charAt(i) == 'R') {

                markerDirection.append(turnRight());

            } else if (userInputedPath.charAt(i) == 'L') {

                markerDirection.append(turnLeft());

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