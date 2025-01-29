package Interception.src.test.java;

import Interception.src.main.java.Decide;
import Interception.src.main.java.Parameters;
import Interception.src.main.java.Point;
import Interception.src.main.java.Declarations;


// File: DeclarationsComputePUMTest.java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PUMTest {
    private Declarations declarations;
    private Parameters params;
    private Declarations.Connectors[][] LCM;
    private boolean[] PUV;
    private Point[] points;

    @BeforeEach
    void setUp() {
        // Initialize Points (dummy data)
        points = new Point[15];
        for (int i = 0; i < 15; i++) {
            points[i] = new Point(0, 0); // Placeholder points
        }

        // Initialize Parameters
        params = new Parameters();
        params.LENGTH1 = 100.0;
        params.RADIUS1 = 50.0;
        params.AREA1 = 200.0;

        // Initialize LCM as a 15x15 NOTUSED matrix
        LCM = new Declarations.Connectors[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                LCM[i][j] = Declarations.Connectors.NOTUSED;
            }
        }

        // Initialize PUV as all true
        PUV = new boolean[15];
        for (int i = 0; i < 15; i++) {
            PUV[i] = true;
        }

        // Initialize Declarations instance
        declarations = new Declarations(15, points, params, LCM, PUV);

        // Mock compute_CMV() by setting CMV directly
        for (int i = 0; i < 15; i++) {
            declarations.getCMV()[i] = true; // Default to true
        }
    }

    // Valid Input Test 1: All Connectors NOTUSED
    @Test
    void testComputePUM_AllNotUsed() {
        // All connectors are already set to NOTUSED in setUp()

        // Compute PUM
        boolean[][] pum = declarations.compute_pum();

        // Assert all PUM elements are true
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                assertTrue(pum[i][j], "PUM[" + i + "][" + j + "] should be true for NOTUSED connector");
            }
        }
    }

    // Valid Input Test 2: Some Connectors ANDD
    @Test
    void testComputePUM_SomeANDD() {
        // Set specific connectors to ANDD
        LCM[0][1] = Declarations.Connectors.ANDD;
        LCM[1][0] = Declarations.Connectors.ANDD; // Ensure symmetry

        LCM[2][3] = Declarations.Connectors.ANDD;
        LCM[3][2] = Declarations.Connectors.ANDD; // Ensure symmetry

        // Set CMV values
        boolean[] CMV = declarations.getCMV();
        CMV[0] = true;
        CMV[1] = false; // ANDD should result in false
        CMV[2] = true;
        CMV[3] = true;  // ANDD should result in true

        // Compute PUM
        boolean[][] pum = declarations.compute_pum();

        // Check PUM[0][1] and PUM[1][0]
        assertFalse(pum[0][1], "PUM[0][1] should be false (ANDD with CMV[0]=true and CMV[1]=false)");
        assertFalse(pum[1][0], "PUM[1][0] should be false (ANDD with CMV[1]=false and CMV[0]=true)");

        // Check PUM[2][3] and PUM[3][2]
        assertTrue(pum[2][3], "PUM[2][3] should be true (ANDD with CMV[2]=true and CMV[3]=true)");
        assertTrue(pum[3][2], "PUM[3][2] should be true (ANDD with CMV[3]=true and CMV[2]=true)");

        // All other PUM elements should remain true (NOTUSED)
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if ((i == 0 && j == 1) || (i == 1 && j == 0) ||
                    (i == 2 && j == 3) || (i == 3 && j == 2)) {
                    continue; // Already tested
                }
                assertTrue(pum[i][j], "PUM[" + i + "][" + j + "] should be true for NOTUSED connector");
            }
        }
    }

    // Valid Input Test 3: Some Connectors ORR
    @Test
    void testComputePUM_SomeORR() {
        // Set specific connectors to ORR
        LCM[4][5] = Declarations.Connectors.ORR;
        LCM[5][4] = Declarations.Connectors.ORR; // Ensure symmetry

        LCM[6][7] = Declarations.Connectors.ORR;
        LCM[7][6] = Declarations.Connectors.ORR; // Ensure symmetry

        boolean[] CMV = declarations.getCMV();
        CMV[4] = false;
        CMV[5] = false; // ORR should result in false
        CMV[6] = true;
        CMV[7] = false; // ORR should result in true

        // Compute PUM
        boolean[][] pum = declarations.compute_pum();

        // Check PUM[4][5] and PUM[5][4]
        assertFalse(pum[4][5], "PUM[4][5] should be false (ORR with CMV[4]=false and CMV[5]=false)");
        assertFalse(pum[5][4], "PUM[5][4] should be false (ORR with CMV[5]=false and CMV[4]=false)");

        // Check PUM[6][7] and PUM[7][6]
        assertTrue(pum[6][7], "PUM[6][7] should be true (ORR with CMV[6]=true or CMV[7]=false)");
        assertTrue(pum[7][6], "PUM[7][6] should be true (ORR with CMV[7]=false or CMV[6]=true)");

        // All other PUM elements should remain true (NOTUSED)
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if ((i == 4 && j == 5) || (i == 5 && j == 4) ||
                    (i == 6 && j == 7) || (i == 7 && j == 6)) {
                    continue; // Already tested
                }
                assertTrue(pum[i][j], "PUM[" + i + "][" + j + "] should be true for NOTUSED connector");
            }
        }
    }

    // Valid Input Test 4: Mixed Connectors (NOTUSED, ANDD, ORR)
    @Test
    void testComputePUM_MixedConnectors() {
        // Set a mix of connectors
        LCM[8][9] = Declarations.Connectors.ANDD;
        LCM[9][8] = Declarations.Connectors.ANDD;

        LCM[10][11] = Declarations.Connectors.ORR;
        LCM[11][10] = Declarations.Connectors.ORR;

        LCM[12][13] = Declarations.Connectors.NOTUSED;
        LCM[13][12] = Declarations.Connectors.NOTUSED;

        boolean[] CMV = declarations.getCMV();
        CMV[8] = true;
        CMV[9] = true;  // ANDD should result in true

        CMV[10] = false;
        CMV[11] = true; // ORR should result in true

        // CMV[12] and CMV[13] are irrelevant due to NOTUSED

        // Compute PUM
        boolean[][] pum = declarations.compute_pum();

        // Check PUM[8][9] and PUM[9][8]
        assertTrue(pum[8][9], "PUM[8][9] should be true (ANDD with CMV[8]=true and CMV[9]=true)");
        assertTrue(pum[9][8], "PUM[9][8] should be true (ANDD with CMV[9]=true and CMV[8]=true)");

        // Check PUM[10][11] and PUM[11][10]
        assertTrue(pum[10][11], "PUM[10][11] should be true (ORR with CMV[10]=false or CMV[11]=true)");
        assertTrue(pum[11][10], "PUM[11][10] should be true (ORR with CMV[11]=true or CMV[10]=false)");

        // Check PUM[12][13] and PUM[13][12]
        assertTrue(pum[12][13], "PUM[12][13] should be true (NOTUSED)");
        assertTrue(pum[13][12], "PUM[13][12] should be true (NOTUSED)");

        // All other PUM elements should remain true (NOTUSED)
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if ((i == 8 && j == 9) || (i == 9 && j == 8) ||
                    (i == 10 && j == 11) || (i == 11 && j == 10) ||
                    (i == 12 && j == 13) || (i == 13 && j == 12)) {
                    continue; // Already tested
                }
                assertTrue(pum[i][j], "PUM[" + i + "][" + j + "] should be true for NOTUSED connector");
            }
        }
    }

    // Invalid Input Test 1: LCM Contains null Connectors (Expecting NullPointerException)
    @Test
    void testComputePUM_LCMContainsNullConnectors() {
        // Set specific connector to null
        LCM[0][1] = null;
        LCM[1][0] = null; // Ensure symmetry

        boolean[] CMV = declarations.getCMV();
        CMV[0] = true;
        CMV[1] = true;

        // Expect NullPointerException when compute_pum() is called
        assertThrows(NullPointerException.class, () -> {
            declarations.compute_pum();
        }, "compute_pum() should throw NullPointerException when LCM contains null connectors");
    }


}
