package Interception.src.test.java;

import Interception.src.main.java.Decide;
import Interception.src.main.java.Parameters;
import Interception.src.main.java.Point;
import Interception.src.main.java.Declarations;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FUVTest {
    private Declarations declarations; 
    private boolean[][] PUM;
    private boolean[] PUV;

    @BeforeEach
    void setUp() {
        int NUMPOINTS = 15;
        Point[] points = new Point[15];
        for (int i = 0; i < 15; i++) {
            points[i] = new Point(i, i); // Simple initialization
        }
        Parameters params = new Parameters();

        // PUV
        PUV = new boolean[]{true, false, true, true, true, false, false, true, true, false, true, true, false, true, true};

        // PUM (generates a 15x15 of false and true statements)
        PUM = new boolean[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                PUM[i][j] = (i == j) || (i + j) % 3 == 0;
            }
        }
        declarations = new Declarations(NUMPOINTS, points, params, null, PUV);
    }
    // PUV is all false
    @Test
    void testFUVAllPUVFalse() {
        PUV = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

        boolean[] expectedFUV = {true, true, true, true, true,true, true, true, true, true, true, true, true, true, true};
        declarations.genFUV(PUM, PUV);

        assertArrayEquals(expectedFUV, declarations.getFUV(), "FUV should be all true when PUV is all false");
    }
    // PUV and PUM all true
    @Test
    void testFUVAllPUVTrueAllPUMTrue() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                PUM[i][j] = true;
            }
        }
        PUV = new boolean[]{true, true, true, true, true,true, true, true, true, true,true, true, true, true, true}; 

        boolean[] expectedFUV = {true, true, true, true, true,true, true, true, true, true, true, true, true, true, true};
        declarations.genFUV(PUM, PUV);

        assertArrayEquals(expectedFUV, declarations.getFUV(), "FUV should be all true when PUV is all true and PUM is all true");
    }
    // PUV mixed false and true
    @Test
    void testFUVMixedPUV() {
        boolean[] expectedFUV = {false, true, false, false, false, true, true, false, false, true, false, false, true, false, false};
        declarations.genFUV(PUM, PUV);

        assertArrayEquals(expectedFUV, declarations.getFUV(), "FUV should match expected behavior based on PUV and PUM");
    }
}