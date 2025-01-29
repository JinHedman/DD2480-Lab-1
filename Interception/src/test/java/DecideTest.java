package Interception.src.test.java;

import Interception.src.main.java.Decide;
import Interception.src.main.java.Parameters;
import Interception.src.main.java.Point;
import Interception.src.main.java.Declarations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class DecideTest {
    private Point[] points;
    private Parameters params;
    private Declarations.Connectors[][] LCM;
    private boolean[] PUV;
    private Decide decide;

    @BeforeEach
    void setUp() {
        // Initialize points (all at origin for simplicity)
        points = new Point[15];
        Arrays.fill(points, new Point(0, 0));

        // Initialize ALL parameters with default values
        params = new Parameters();
        params.LENGTH1 = 1.0;
        params.RADIUS1 = 1.0;
        params.EPSILON = Math.PI / 4;
        params.AREA1 = 1.0;
        params.Q_PTS = 2;
        params.QUADS = 1;
        params.DIST = 1.0;
        params.N_PTS = 3;
        params.K_PTS = 1;
        params.A_PTS = 1;
        params.B_PTS = 1;
        params.C_PTS = 1;
        params.D_PTS = 1;
        params.E_PTS = 1;
        params.F_PTS = 1;
        params.G_PTS = 1;
        params.LENGTH2 = 1.0;
        params.RADIUS2 = 1.0;
        params.AREA2 = 1.0;

        LCM = new Declarations.Connectors[15][15];
        for (Declarations.Connectors[] row : LCM) {
            Arrays.fill(row, Declarations.Connectors.NOTUSED);
        }
        PUV = new boolean[15];
        Arrays.fill(PUV, true);
    }

    /*
     *  Tests that the Decide class correctly determines whether to launch or not when parameters are correctly set. 
     *  All LCM connectors are NOTUSED → FUV is all true → launch allowed
     */ 
    @Test
    void decide_AllPUMTrue_ReturnsTrue() {
        decide = new Decide(15, points, params, LCM, PUV);
        assertTrue(decide.decide());
    }

    /*
     * Tests that Decide returns false when at least one PUM is false.
     * LCM[0][0] = AND with CMV[0] = false → FUV[0] = false → launch denied
     */
    @Test
    void decide_PUMFalse_ReturnsFalse() {
        LCM[1][0] = Declarations.Connectors.ANDD;
        LCM[0][1] = Declarations.Connectors.ANDD;
        decide = new Decide(15, points, params, LCM, PUV);
        assertFalse(decide.decide());
    }

    /*
     *  Tests that Decide returns true when at least one PUV is false.
     *  PUV[0] = false → FUV[0] is true regardless of PUM → launch allowed
     */
    @Test
    void decide_PUVFalse_FUVTrueForEntry() {
        PUV[0] = false;
        LCM[0][0] = Declarations.Connectors.ANDD; // Irrelevant due to PUV[0] = false
        decide = new Decide(15, points, params, LCM, PUV);
        assertTrue(decide.decide());
    }

    /*
     * Tests that Decide returns true when all PUV entries are false.
     * All PUV entries are false → FUV is all true → launch allowed
     */
    @Test
    void decide_AllPUVFalse_FUVAllTrue() {
        Arrays.fill(PUV, false);
        decide = new Decide(15, points, params, LCM, PUV);
        assertTrue(decide.decide());
    }
}