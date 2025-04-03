package ca.mcmaster.se2aa4.mazerunnertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.String;
import java.util.*;

import ca.mcmaster.se2aa4.mazerunner.MazeRunnerGamePathMachine;

public class MainTest {

    String file = "./examples/direct.maz.txt";

    @Test
    public void testVerifyMazePath() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        assertTrue(mazeRunnerGame.verifyMazePath("FRFFLFFFRFLFRFLFF"), "This is the correct path");

    }

    @Test
    public void testConvertCanonicalToFactorized() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        assertEquals(mazeRunnerGame.convertCanonicalToFactorized("FRFFLFFFRFLFRFLRR"), "1F1R2F1L3F1R1F1L1F1R1F1L2R");

    }

    @Test
    public void testChooseToTurn() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        assertEquals(mazeRunnerGame.chooseToTurn(), new String(""));

    }

    @Test
    public void testVerifyNextMovement() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        assertTrue(mazeRunnerGame.verifyNextMovement());

    }

    @Test
    public void testTurnRight() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        assertEquals(mazeRunnerGame.turnRight(), "South");

    }

    @Test
    public void testTurnLeft() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        assertEquals(mazeRunnerGame.turnLeft(), "North");

    }

    @Test
    public void testMoveForward() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        assertEquals(mazeRunnerGame.moveForward()[1], 1);

    }

    @Test
    public void testFindWestSideEntrance() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        int[] initialPosition = {1,0};
        assertArrayEquals(mazeRunnerGame.findWestSideEntrance(), initialPosition, "Same Starting positions");

    }

    @Test
    public void testCreateMapArray() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        String[][] generatedMap = {{"#","#","#","#","#","#","#","#"},{" "," ","#","#","#","#","#","#"},{"#"," ","#","#","#","#","#","#"},{"#"," "," "," "," "," ","#","#"},{"#","#","#","#"," "," ","#","#"},{"#","#","#","#","#"," "," "," "},{"#","#","#","#","#","#","#","#"}};
        assertArrayEquals(mazeRunnerGame.createMapArray(file), generatedMap, "Same Map generated");

    }    

    @Test
    public void testGetMarkerPosition() {

        MazeRunnerGamePathMachine mazeRunnerGame = new MazeRunnerGamePathMachine(file);
        int[] initialPosition = {1,0};
        assertArrayEquals(mazeRunnerGame.getMarkerPosition(), initialPosition, "Same Starting positions");

    }  
}