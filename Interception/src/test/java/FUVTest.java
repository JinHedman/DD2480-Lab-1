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
        int NUMPOINTS = 5;
        Point[] points = {
            new Point(0, 0), new Point(1, 1), new Point(2, 2),
            new Point(3, 3), new Point(4, 4)
        };
        Parameters params = new Parameters();

        // PUV
        PUV = new boolean[]{true, false, true, true, true};

        // PUM
        PUM = new boolean[][] {
            {true, false, true, false, true},  // Row 0
            {false, true, true, true, true},   // Row 1
            {true, true, true, true, true},    // Row 2
            {false, true, true, true, false},  // Row 3
            {true, true, true, true, true}     // Row 4
        };

        declarations = new Declarations(NUMPOINTS, points, params, null, PUV);
    }
    // PUV is all false
    @Test
    void testFUVAllPUVFalse() {
        PUV = new boolean[]{false, false, false, false, false};

        boolean[] expectedFUV = {true, true, true, true, true};
        boolean[] actualFUV = declarations.genFUV(PUM, PUV);

        assertArrayEquals(expectedFUV, actualFUV, "FUV should be all true when PUV is all false");
    }
    // PUV and PUM all true
    @Test
    void testFUVAllPUVTrueAllPUMTrue() {
        PUM = new boolean[][] { 
            {true, true, true, true, true},
            {true, true, true, true, true},
            {true, true, true, true, true},
            {true, true, true, true, true},
            {true, true, true, true, true}
        };
        PUV = new boolean[]{true, true, true, true, true}; 

        boolean[] expectedFUV = {true, true, true, true, true};
        boolean[] actualFUV = declarations.genFUV(PUM, PUV);

        assertArrayEquals(expectedFUV, actualFUV, "FUV should be all true when PUV is all true and PUM is all true");
    }
    // PUV mixed false and true
    @Test
    void testFUVMixedPUV() {
        boolean[] expectedFUV = {false, true, true, false, true};
        boolean[] actualFUV = declarations.genFUV(PUM, PUV);

        assertArrayEquals(expectedFUV, actualFUV, "FUV should match expected behavior based on PUV and PUM");
    }
}