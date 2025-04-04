package ca.mcmaster.se2aa4.mazerunnertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

import java.lang.String;

import ca.mcmaster.se2aa4.mazerunner.Player;
import ca.mcmaster.se2aa4.mazerunner.PlayerMarker;
import ca.mcmaster.se2aa4.mazerunner.MazeGame;
import ca.mcmaster.se2aa4.mazerunner.enums.*;
import ca.mcmaster.se2aa4.mazerunner.PathInstructionActions;

public class MainTest {

    String file = "./examples/direct.maz.txt";

    @Test
    public void testVerifyMazePath() {

        Player player = new Player(file, "FRFFLFFFRFLFRFLFF");
        assertTrue(player.verifyMazePath(), "This is the correct path");

    }

    @Test
    public void testConvertCanonicalToFactorized() {

        PathInstructionActions pathInstructionActions = new PathInstructionActions();
        assertEquals(pathInstructionActions.convertToFactorizedPath("FRFFLFFFRFLFRFLRR"), "1F1R2F1L3F1R1F1L1F1R1F1L2R");

    }

    @Test
    public void testChooseToTurn() {

        Player player = new Player(file, null);
        int[] initialPosition = {1,0};

        assertEquals(player.makeTurningDecision(Direction.East,initialPosition).write(), new String(""));

    }

    @Test
    public void testVerifyNextMovement() {

        Player player = new Player(file, null);
        int[] initialPosition = {1,0};

        assertTrue(player.verifyNextMovement(Direction.East,initialPosition));

    }

    @Test
    public void testTurnRight() {

        PlayerMarker marker = new PlayerMarker();
        assertEquals(marker.turnRight(Direction.East), Direction.South);

    }

    @Test
    public void testTurnLeft() {

        PlayerMarker marker = new PlayerMarker();
        assertEquals(marker.turnLeft(Direction.East),  Direction.North);

    }

    @Test
    public void testMoveForward() {

        PlayerMarker marker = new PlayerMarker();
        int[] initialPosition = {1,0};

        assertEquals(marker.moveForward(Direction.East, initialPosition)[1], 1);

    }

    @Test
    public void testFindWestSideEntrance() {

        MazeGame maze = new MazeGame(file);
        int[] initialPosition = {1,0};
        assertArrayEquals(maze.findWestSideEntrance(file), initialPosition, "Same Starting positions");

    }

    @Test
    public void testCreateMapArray() {

        MazeGame maze = new MazeGame(file);
        PathBlock[][] generatedMap = {{PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL},{PathBlock.PASS,PathBlock.PASS,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL},{PathBlock.WALL,PathBlock.PASS,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL},{PathBlock.WALL,PathBlock.PASS,PathBlock.PASS,PathBlock.PASS,PathBlock.PASS,PathBlock.PASS,PathBlock.WALL,PathBlock.WALL},{PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.PASS,PathBlock.PASS,PathBlock.WALL,PathBlock.WALL},{PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.PASS,PathBlock.PASS,PathBlock.PASS},{PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL,PathBlock.WALL}};
        assertArrayEquals(maze.createMaze(file), generatedMap, "Same Map generated");

    }    

    @Test
    public void testGetMarkerPosition() {

        Player player = new Player(file, null);
        int[] initialPosition = {1,0};
        assertArrayEquals(player.getMarkerPosition(), initialPosition, "Same Starting positions");

    }  
}