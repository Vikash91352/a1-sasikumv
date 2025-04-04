package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import ca.mcmaster.se2aa4.mazerunner.enums.*;
import ca.mcmaster.se2aa4.mazerunner.observer.MazeSubject;
import ca.mcmaster.se2aa4.mazerunner.observer.ObservingMazePlayer;

public class MazeGame extends MazeSubject {

    private PathBlock[][] mazeRunnerMap;
    private int state = 1;

    public MazeGame(String mapFile) {

        mazeRunnerMap = new PathBlock[createMaze(mapFile).length][createMaze(mapFile)[0].length];
        mazeRunnerMap = createMaze(mapFile);

    }

    public PathBlock[][] createMaze(String mapFile) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(mapFile));

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
    
            reader.close(); 
    
            PathBlock[][] mazeMapArray = new PathBlock[mazeMapArrayList.size()][];
    
            //Converting 2d ArrayList to 2d Array
            for (int i = 0; i < mazeMapArrayList.size(); i++) {
                ArrayList<String> mazeRowArrayList = mazeMapArrayList.get(i);
    
                PathBlock[] mazeRowArray = new PathBlock[mazeMapArrayList.get(0).size()];
    
                for (int j = 0; j < mazeRowArrayList.size(); j++) {
                    mazeRowArray[j] = PathBlock.fromString(mazeRowArrayList.get(j));
                }
    
                if (mazeRowArrayList.size() < mazeMapArrayList.get(0).size()) {
                    for (int j = mazeRowArrayList.size(); j < mazeMapArrayList.get(1).size(); j++) {
                        mazeRowArray[j] = PathBlock.PASS;
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

    public int getState(){

        return this.state;

    }

    public void setState(int state) {

        this.state = state;
        this.notifyAllObservers();

    }

    public void notifyAllObservers(){
        for (ObservingMazePlayer observer : super.players){
            observer.update(mazeRunnerMap);
        }
    }

    public int[] findWestSideEntrance(String mapFile) {

        int[] initialPosition = new int[2];

        for (int i = 0; i < mazeRunnerMap.length; i++) {

            if (mazeRunnerMap[i][0] == PathBlock.PASS) {

                initialPosition[0] = i;
                initialPosition[1] = 0;

            }

        }

        return initialPosition;

    }

}