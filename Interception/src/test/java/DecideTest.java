package Interception.src.test.java; 

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;

import Interception.src.main.java.Decide;
import Interception.src.main.java.Parameters;
import Interception.src.main.java.Point;
import Interception.src.main.java.Declarations;

class DecideTest {
  

//------------------------LIC1---------------------------------------


    /**
     * Tests the “true” scenario:
     *  - There exists at least one set of three consecutive points whose minimum
     *    enclosing circle has radius strictly greater than RADIUS1.
     */
    void testLic1True_Advanced() {
    
        // Scenario 1: Exactly 3 points that obviously won't fit in radius = 4
        Parameters paramsA = new Parameters();
        paramsA.RADIUS1 = 4.0;
        Point[] pointsA = new Point[] {
            new Point(0, 0),
            new Point(10, 0),   
            new Point(10, 1)  
        };
        Declarations decA = new Declarations(3, pointsA, paramsA, null, null);
        assertTrue(decA.compute_lic_1(),"Expected true because these 3 consecutive points require a circle radius > 4.");
    
        // Scenario 2: More points (5 points total),
        // with at least one triplet that doesn't fit in radius = 5.0
        Parameters paramsB = new Parameters();
        paramsB.RADIUS1 = 5.0;
        Point[] pointsB = new Point[] {
            new Point(0, 0),  // p0
            new Point(2, 2),  // p1
            new Point(6, 7),  // p2
            new Point(7, 1),  // p3
            new Point(10,10)  // p4
        };
        
        Declarations decB = new Declarations(5, pointsB, paramsB, null, null);
    
        // This should be true, since the final triplet (p2, p3, p4) won't fit.
        assertTrue( decB.compute_lic_1(),  "At least one of the triplets among these 5 points should require a circle radius > 5.");
    
        Parameters paramsC = new Parameters();
        paramsC.RADIUS1 = 4.0;
        Point[] pointsC = {
            new Point(0, 0),  
            new Point(3, 4),  
            new Point(4, -1),  
            new Point(10, 10) 
        };
    
        Declarations decC = new Declarations(4, pointsC, paramsC, null, null);
    
        assertTrue( decC.compute_lic_1(), "Expected true because at least one set of (3) consecutive points likely needs radius > 4.");
    }

    /**
     * Tests the “false” scenario:
     * 1) If NUMPOINTS < 3, we cannot form a consecutive triplet -> return false.
     * 2) If all consecutive triplets fit in a circle of radius RADIUS1, return false.
     */
    @Test
    void testLic1False() {
        // Case A: NUMPOINTS < 3 => automatically false
        Parameters paramsFew = new Parameters();
        paramsFew.RADIUS1 = 5.0;
        Point[] fewPoints = new Point[] {
            new Point(0, 0),
            new Point(1, 1)
        };
        Declarations decFew = new Declarations(2, fewPoints, paramsFew, null, null);
        assertFalse(decFew.compute_lic_1(), "Should return false when fewer than 3 points are available.");

        // Case B: Three consecutive points that DO fit in a circle of radius 5
        Parameters params = new Parameters();
        params.RADIUS1 = 5.0;
        Point[] fitPoints = new Point[] {
            new Point(0, 0),
            new Point(3, 0),
            new Point(3, 4)  
        };
        Declarations decFit = new Declarations(3, fitPoints, params, null, null);
        assertFalse( decFit.compute_lic_1(), "Should return false when the three consecutive points do fit in a circle of radius 5.");
    }

    /**
     * Tests “invalid input” according to the contract:
     * RADIUS1 must satisfy 0 ≤ RADIUS1. If RADIUS1 < 0, we expect false.
     */
    @Test
    void testLic1InvalidInput() {
        Parameters invalidParams = new Parameters();
        invalidParams.RADIUS1 = -1; // invalid
        int numPoints = 3;

        Point[] points = new Point[] {
            new Point(0, 0),
            new Point(3, 0),
            new Point(3, 4)
        };
        Declarations dec = new Declarations(numPoints, points, invalidParams, null, null);

        // Assertion verifying the contract:
        // "Should return false because RADIUS1 < 0 is invalid."
         assertFalse(dec.compute_lic_1(), "Should return false for invalid (negative) RADIUS1." );
    }
//----------------------------------------------------------------------



    @Test
    void testLic12True() {
        Parameters params = new Parameters();
        params.K_PTS = 1;
        params.LENGTH1 = 3;
        params.LENGTH2 = 2;
        Point[] points = {
            new Point(0, 0),
            new Point(0, 0),
            new Point(4, 0),
            new Point(1, 0)
        };
        
        Declarations decide = new Declarations(4, points, params, null, null);
        assertTrue(decide.compute_lic_12());
    }

    // Distances: 2 and 4. There is no distance smaller than LENGTH2 → LIC12 should fail
    @Test
    void testLic12FalseDueToDistances() {
        Parameters params = new Parameters();
        params.K_PTS = 1; 
        params.LENGTH1 = 3;
        params.LENGTH2 = 1;

        Point[] points = {
            new Point(0, 0),
            new Point(0, 0),
            new Point(4, 0),
            new Point(2, 0)
        };
        Declarations decide = new Declarations(4, points, params, null, null);
        assertFalse(decide.compute_lic_12());
    }

    // Only 2 points → LIC12 should fail immediately
    @Test
    void testLic12FalseDueToFewerThan3Points() {
        Parameters params = new Parameters();
        params.K_PTS = 1;
        params.LENGTH1 = 3;
        params.LENGTH2 = 1;

        Point[] points = {new Point(0,0), new Point(1,1)};
        Declarations decide = new Declarations(2, points, params, null, null);
        assertFalse(decide.compute_lic_12());
    }

    // Only 4 points → no valid pairs with 3 intervening points
    @Test
    void testLic12FalseDueToNoValidPairs() {
        Parameters params = new Parameters();
        params.K_PTS = 3;
        params.LENGTH1 = 3;
        params.LENGTH2 = 1;

        Point[] points = {
            new Point(0, 0),
            new Point(0, 0),
            new Point(4, 0),
            new Point(1, 0)
        };
        Declarations decide = new Declarations(4, points, params, null, null);
        assertFalse(decide.compute_lic_12());
    }

    

   /*
    * 
    * 
    */



//------------------------LIC0 TESTS---------------------------------------

    /**
     * Test for the “true” scenario under the contract:
     * There is at least one pair of consecutive points whose distance > LENGTH1.
     */
    @Test
    void testLic0True() {
        // Setup
        Parameters params = new Parameters();
        params.LENGTH1 = 3; 
        int numPoints = 3;

        // Points (1,5) -> (5,4) are > 3 units apart (distance ~ 4.12)
        Point[] points = new Point[] {
            new Point(1, 5),
            new Point(5, 4),
            new Point(5, 6)
        };

        Declarations dec = new Declarations(numPoints, points, params, null, null);

        // Assertion verifying the contract:
        // "Expected true because at least one consecutive pair of points is
        //  more than 3 units apart."
        assertTrue(  dec.compute_lic_0(), "Should return true when a consecutive pair exceeds LENGTH1.");
    }

    /**
     * Test for the “false” scenario under the contract:
     * 1) Either no consecutive pair is greater than LENGTH1,
     *    or 2) We have fewer than 2 points.
     */
    @Test
    void testLic0False() {
        // Setup
        Parameters params = new Parameters();
        params.LENGTH1 = 3; 
        int numPoints = 3;

        // All distances <= 3
        Point[] points = new Point[] {
            new Point(1, 2),
            new Point(2, 3),
            new Point(3, 4)
        };
        Declarations dec = new Declarations(numPoints, points, params, null, null);

        // Assertion verifying the contract:
        // "Should be false because no pair of consecutive points is more
        //  than 3 units apart."
        assertFalse(dec.compute_lic_0(),
            "Should return false when no consecutive pair exceeds LENGTH1."
        );

        // If we have fewer than 2 points should it return false.
        int fewPointsCount = 1;
        Point[] singlePointArray = new Point[] {new Point(0, 0)  };
        Declarations decFew = new Declarations(fewPointsCount, singlePointArray, params, null, null);
        assertFalse( decFew.compute_lic_0(),"Should return false when NUMPOINTS < 2 (no consecutive points).");
    }

    /**
     * Test for “invalid input” according to the contract:
     * LENGTH1 must satisfy 0 ≤ LENGTH1. If LENGTH1 < 0, we expect false.
     */
    @Test
    void testLic0InvalidInput() {
        Parameters params = new Parameters();
        params.LENGTH1 = -1; 
        int numPoints = 3;
        Point[] points = new Point[] {
            new Point(1, 2),
            new Point(2, 3),
            new Point(3, 4)
        };
        Declarations dec = new Declarations(numPoints, points, params, null, null);

        assertFalse(dec.compute_lic_0(), "Should return false for invalid (negative) LENGTH1.");
    }


  
//------------------------------------------------------------------
  
    /*
     * Function for testing if LIC6 is true, which means testing if there exists at least one set of N_PTS consecutive data points such that at 
     * least one of the points lies a distance greater than DIST from the line joining the first and last of these N_PTS points. If the first and 
     * last points of these N PTS are identical, then the calculated distance to compare with DIST will be the distance from the coincident point 
     * to all other points of the N PTS consecutive points.
     */
    @Test
    void testLic6True(){
        Parameters params = new Parameters();
        params.DIST = 2.0; 
        params.N_PTS = 3;
        int NUMPOINTS = 3;
        
        Point[] points = new Point[] {new Point(0, 0), new Point(1, 3), new Point(2, 0)};     
        Declarations trueDecide = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(trueDecide.compute_lic_6());


        Point[] falsePoints = new Point[] {new Point(0, 0), new Point(1, 1), new Point(2, 2)};     
        Declarations falseDecide = new Declarations(NUMPOINTS, falsePoints, params, null, null);
        assertFalse(falseDecide.compute_lic_6());
    }


  
  /*
     * Function for testing if LIC9 returns the correct boolean value.   
     * Returns true when the three points create a angle which satisfiy angle < (PI−EPSILON) or angle > (PI+EPSILON).
     * Returns false if the three points create a angle which does not satisfy the above statement or if NUMPOINTS < 5, 1 ≤ C PTS, 1 ≤ D PTS, C PTS+D PTS ≤ NUMPOINTS−3.
     */
    @Test
    void testLic9() {
        Parameters params = new Parameters();
        params.C_PTS = 1;
        params.D_PTS = 1;
        params.EPSILON = 0.1;
        
        int NUMPOINTS = 5;
        Point[] points = new Point[] {
            new Point(0, 0),  // A (first)
            new Point(1, 1),
            new Point(2, 2),  // B (second)
            new Point(3, 1),
            new Point(4, 0)   // C (third)
        };

        Declarations declarations = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(declarations.compute_lic_9(), "LIC9 should return true when an angle outside the range is found");

        Point[] collinearPoints = new Point[] {
            new Point(0, 0), new Point(2, 2), new Point(4, 4), new Point(6, 6), new Point(8, 8)
        };
        Declarations collinearDecide = new Declarations(NUMPOINTS, collinearPoints, params, null, null);
        assertFalse(collinearDecide.compute_lic_9(), "LIC9 should return false for collinear points");
    }
  
  
    /*
     * Function for testing if LIC8 is true, which means testing if there exists at least one set of three data points separated by exactly A_PTS 
     * and B_PTS consecutive intervening points, respectively, that cannot be contained within or on a circle of radius RADIUS1.
     */
    @Test
    void testLic8True(){
        Parameters params = new Parameters();
        params.A_PTS= 1;
        params.B_PTS= 1;
        params.RADIUS1 = 2.0;
        int NUMPOINTS = 5;
        
        Point[] points = new Point[] {new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(5, 0)};     
        
        Declarations trueDecide = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(trueDecide.compute_lic_8());

        Point[] falsePoints = new Point[] {new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 0), new Point(4, 0)};     
        
        Declarations falseDecide = new Declarations(NUMPOINTS, falsePoints, params, null, null);
        assertFalse(falseDecide.compute_lic_8());


    }


    /*
     * Function for testing if LIC5 returns the correct boolean value.
     * Returns true when two consecutive points where the x coordinate of the second points is less then the x cordinte of the first point. 
     * Returns false when NUMPOINTS is less than 2 or/and when it is not possible to find two consecutive points where the x coordinate of the second point
     * is less than the x coordinate of the first points. 
     */
    @Test
    void testLic5(){
        Parameters params = new Parameters();
        int NUMPOINTS = 2; 
        // should return when the condition is met with only two points. 
        Point[] corrPoints = new Point[]{new Point(5, 5), new Point(3, 5)}; 
        Declarations corrDecide = new Declarations(NUMPOINTS, corrPoints, params, null, null);
        assertTrue(corrDecide.compute_lic_5(),"Should return true when a consecutive pair meets the condition.");
  

        // Should also return true with more than two points where the conditions is not necessarily met by the first pair of points. 
        NUMPOINTS = 4; 
        Point[] fewTruePoints = new Point[]{new Point(1, 1),  new Point(3, 2),  new Point(2, 2), new Point(4, 4) };
        Declarations fewCorrDecide = new Declarations(NUMPOINTS, fewTruePoints, params, null, null);
        assertTrue(fewCorrDecide.compute_lic_5(),"Should return true when at least one consecutive pair meets the condition.");

        // should return false when the number of points is less than two.
        NUMPOINTS = 1;
        Point[] fewPoints = new Point[] {new Point(0, 0)}; 
        Declarations fewPointsDecide = new Declarations(NUMPOINTS, fewPoints, params, null, null);
        assertFalse(fewPointsDecide.compute_lic_5(),"Should return false when there are fewer than two points.");

        // should return false when there are no consecutive pairs meet the condition.
        NUMPOINTS = 4;
        Point[] falsePoints = new Point[]{ new Point(1, 1), new Point(2, 2),  new Point(3, 3), new Point(4, 4)};
        Declarations falsePointsDecide = new Declarations(NUMPOINTS, falsePoints, params, null, null);
        assertFalse(falsePointsDecide.compute_lic_5(),"Should return false when no consecutive pairs meet the condition.");
    }


    /*
     * Function for testing if LIC4 returns the correct boolean values.
     * Returns true when a set of Q_pts lies in more than QUADS quadrants.
     * Returns false when Q_Pts is less than two
     */
    @Test 
    void testLic4(){
        Parameters params = new Parameters();

        // Should return true when a set of Q_pts lies in more than Quads quadrants
        params.Q_PTS = 3;
        params.QUADS = 2;
        int NUMPOINTS = 5;
        // Points covering Quadrants I, II, III
        Point[] truePoints = new Point[]{new Point(1, 1), new Point(-1, 1), new Point(-1, -1),  new Point(1, -1),  new Point(2, 2)};
        Declarations trueDecide = new Declarations(NUMPOINTS, truePoints, params, null, null);
        assertTrue(trueDecide.compute_lic_4(), "Should return true when a set of Q_PTS points lies in more than QUADS quadrants");

        // Should return false when Q_pts is less than two. 
        params.Q_PTS = 1; 
        params.QUADS = 1;
        // Can use the same points
        Declarations lessQ_pts = new Declarations(NUMPOINTS, truePoints, params, null, null);
        assertFalse(lessQ_pts.compute_lic_4(),"Should return false when Q_PTS < 2");
    
        // Should return false when Q_pts is greater than NUMPOINTS.
        params.Q_PTS = 6;
        params.QUADS = 2;
        Declarations tooGreatQ_points = new Declarations(NUMPOINTS, truePoints, params, null, null);
        assertFalse(tooGreatQ_points.compute_lic_4(),"Should return false when Q_PTS > NUMPOINTS");
        
        // Should return false when QUADS is less than 1. 
        params.QUADS = 0; // invalid
        Declarations tooSmallQuads = new Declarations(NUMPOINTS, truePoints, params, null, null);
        assertFalse(tooSmallQuads.compute_lic_4(),
        "Should return false when QUADS < 1");

        // Should return false when QUADS is greater than 3.
        params.QUADS = 4; // invalid
        NUMPOINTS = 5;
        Declarations tooLargeQuads = new Declarations(NUMPOINTS, truePoints, params, null, null);
        assertFalse(tooLargeQuads.compute_lic_4(),"Should return false when QUADS > 3");

        // Should return false when no set of Q_pts lie in more than QUADS quadrants. 
        params.Q_PTS = 3;
        params.QUADS = 2;
        // All points lie in Quadrant I and II only
        Point[] falsePoints = new Point[]{
                new Point(1, 1),   // I
                new Point(-1, 1),  // II
                new Point(2, 2),   // I
                new Point(-2, 1),  // II
                new Point(3, 3)    // I
        };
        Declarations falseCase = new Declarations(NUMPOINTS, falsePoints, params, null, null);
         assertFalse(falseCase.compute_lic_4(),
                "Should return false when no set of Q_PTS points lies in more than QUADS quadrants");

    }
  


//------------------------ LIC3 ---------------------------------------

    /**
     * Tests the “true” scenario:
     * - There exists at least one set of three consecutive points whose triangle 
     *   area is strictly greater than AREA1.
     */
    @Test
    void testLic3True() {
        Parameters params = new Parameters();
        params.AREA1 = 1.0;  // we want to see if area > 1 is possible
        int numPoints = 5;

        Point[] points = new Point[] {
            new Point(0, 0),  // p[0]
            new Point(2, 0),  // p[1]
            new Point(0, 2),  // p[2] -> area = 2 > 1
            new Point(1, 1),  // p[3]
            new Point(3, 3)   // p[4]
        };
        Declarations dec = new Declarations(numPoints, points, params, null, null);

        // Verify
        assertTrue(dec.compute_lic_3(), "Should return true because (p[0], p[1], p[2]) form a triangle with area = 2 > 1.");
    }

    /**
     * Tests the “false” scenario:
     * 1) If NUMPOINTS < 3, we automatically have no triplets, so return false.
     * 2) If no consecutive triplet has area > AREA1, return false.
     */
    @Test
    void testLic3False() {
        // Case A: Fewer than 3 points
        Parameters paramsFew = new Parameters();
        paramsFew.AREA1 = 1.0;
        Point[] fewPoints = new Point[] {
            new Point(0, 0),
            new Point(1, 1)
        };
        Declarations decFew = new Declarations(2, fewPoints, paramsFew, null, null);
        assertFalse(decFew.compute_lic_3(),"Should return false when fewer than 3 points are provided.");

        // Case B: Three consecutive points that form a triangle with area <= AREA1
        Parameters paramsSmall = new Parameters();
        paramsSmall.AREA1 = 1.0;
        int numPoints = 3;
        Point[] smallAreaPoints = new Point[] {
            new Point(0, 0),
            new Point(1, 1),
            new Point(2, 2)  // collinear => area = 0
        };
        Declarations decSmall = new Declarations(numPoints, smallAreaPoints, paramsSmall, null, null);
        assertFalse( decSmall.compute_lic_3(), "Should return false when all consecutive triplets have area <= 1 (collinear => area=0)." );

    }

    /**
     * Tests “invalid input” according to the contract:
     * - AREA1 must satisfy 0 ≤ AREA1. If AREA1 < 0, the function returns false.
     */
    @Test
    void testLic3InvalidInput() {
        Parameters invalidParams = new Parameters();
        invalidParams.AREA1 = -1.0; // invalid
        int numPoints = 3;

        Point[] points = new Point[] {
            new Point(0, 0),
            new Point(2, 0),
            new Point(0, 2)
        };

        Declarations dec = new Declarations(numPoints, points, invalidParams, null, null);

        // Assertion verifying the contract:
        // "Should return false because AREA1 < 0 is invalid."
        assertFalse(dec.compute_lic_3(),"Should return false for invalid (negative) AREA1.");
    }

//-------------------------------------------------------------------

    /*
     * Function for testing if LIC11 returns the correct boolean value.   
     * Returns true if there exist two points with exacly G_PTS consecutive intervening points such that X[j] - X[i] < 0. (where i < j ).
     * Returns false if the condition is not met or if NUMPOINTS < 3, 1 ≤ G PTS ≤ NUMPOINTS−2.
     */
    @Test
    void testLic11() {
        Parameters params = new Parameters();
        params.G_PTS = 1;
        
        int NUMPOINTS = 5;
        Point[] points = new Point[] {
            new Point(5, 1), 
            new Point(4, 2),
            new Point(3, 3),  
            new Point(2, 4),  
            new Point(1, 5)   
        };
        
        // true case
        Declarations decreasingXDecide = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(decreasingXDecide.compute_lic_11(), "LIC11 should return true");
    
        // false case
        Point[] increasingXPoints = new Point[] {
            new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4), new Point(5, 5)
        };
        Declarations increasingXDecide = new Declarations(NUMPOINTS, increasingXPoints, params, null, null);
        assertFalse(increasingXDecide.compute_lic_11(), "LIC11 should return false");
    }
    
    /*
     * Function for testing if LIC14 is true, which means testing if there exists at least one set of three data points, separated by exactly
     * E_PTS and F_PTS consecutive intervening points, respectively, that are the vertices of a triangle with area greater than AREA1. 
     * In addition, there exist three data points (which can be the same or different from the three data points just mentioned) separated by 
     * exactly E_PTS and F_PTS consecutive intervening points, respectively, that are the vertices of a triangle with area less than AREA2. 
     * Both parts must be true for the LIC to be true.
     */
    @Test
    void testLic14True(){
        Parameters params = new Parameters();
        params.E_PTS= 1;
        params.F_PTS= 1;
        params.AREA1 = 2.0;
        params.AREA2 = 8.0;
        int NUMPOINTS = 5;
        
        Point[] points = new Point[] {new Point(0, 0), new Point(1, 1), new Point(3, 0), new Point(2, 2), new Point(0, 4)};     
        
        Declarations trueDecide = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(trueDecide.compute_lic_14());

        Point[] falsePoints = new Point[] {new Point(0, 0), new Point(1, 1), new Point(0, 10), new Point(2, 2), new Point(2, 0)};     
        
        Declarations falseDecide = new Declarations(NUMPOINTS, falsePoints, params, null, null);
        assertFalse(falseDecide.compute_lic_14());
    }
   

    /*
     * Function for testing LIC13 returns expected value for the given instances.
     */
    @Test
    void testLic13() {
        Parameters params = new Parameters();
        params.A_PTS = 1;
        params.B_PTS = 1;
        params.RADIUS1 = 1.5;
        params.RADIUS2 = 2.5;
        int NUMPOINTS = 5;

        // Test case where a triplet meets both conditions (radius > RADIUS1 and <= RADIUS2)
        Point[] pointsTrue = new Point[] {
            new Point(0, 0),
            new Point(1, 1),
            new Point(4, 0),
            new Point(3, 3),
            new Point(7, 0)
        };
        Declarations decideTrue = new Declarations(NUMPOINTS, pointsTrue, params, null, null);
        assertTrue(decideTrue.compute_lic_13());

        // Test case where no triplet meets both conditions
        params.RADIUS1 = 3.0;
        params.RADIUS2 = 1.0;
        Declarations decideFalse = new Declarations(NUMPOINTS, pointsTrue, params, null, null);
        assertFalse(decideFalse.compute_lic_13());

        // Test case with insufficient points (NUMPOINTS < 5)
        int smallNumPoints = 4;
        Point[] smallPoints = new Point[4];
        for (int i = 0; i < smallNumPoints; i++) {
            smallPoints[i] = new Point(i, i);
        }
        Declarations decideSmall = new Declarations(smallNumPoints, smallPoints, params, null, null);
        assertFalse(decideSmall.compute_lic_13());

        // Test case where maxI is negative (A_PTS and B_PTS too large)
        params.A_PTS = 3;
        params.B_PTS = 3;
        int numPoints = 5;
        Point[] maxIPoints = new Point[numPoints];
        for (int i = 0; i < numPoints; i++) {
            maxIPoints[i] = new Point(i, i);
        }
        Declarations decideMaxI = new Declarations(numPoints, maxIPoints, params, null, null);
        assertFalse(decideMaxI.compute_lic_13());

        // Test case with two triplets: one meets condition1, the other condition2
        params.A_PTS = 1;
        params.B_PTS = 1;
        params.RADIUS1 = 3.0;
        params.RADIUS2 = 1.0;
        int numPointsTwo = 6;
        Point[] pointsTwo = new Point[] {
            new Point(0, 0),
            new Point(0, 1),
            new Point(8, 0),
            new Point(0, 3),
            new Point(8, 0),
            new Point(0, 5)
        };
        Declarations decideTwo = new Declarations(numPointsTwo, pointsTwo, params, null, null);
        assertTrue(decideTwo.compute_lic_13());
    }

    /*
     * Function for testing if LIC10 is true, which means testing if there exists at least one set of three data points separated by exactly E_PTS 
     * and F_PTS consecutive intervening points, respectively, that are the vertices of a triangle with area greater than AREA1.
     */
    @Test
    void testLic10True(){
        Parameters params = new Parameters();
        params.E_PTS= 1;
        params.F_PTS= 1;
        params.AREA1 = 2.0;
        int NUMPOINTS = 5;
        
        Point[] points = new Point[] {new Point(0, 0), new Point(1, 1), new Point(3, 0), new Point(2, 2), new Point(0, 4)};     
        
        Declarations trueDecide = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(trueDecide.compute_lic_10());

        Point[] falsePoints = new Point[] {new Point(0, 0), new Point(1, 1), new Point(0, 3), new Point(2, 2), new Point(0, 4)};     
        
        Declarations falseDecide = new Declarations(NUMPOINTS, falsePoints, params, null, null);
        assertFalse(falseDecide.compute_lic_10());

    }


    
    /*
     * Function for testing if LIC7 works correctly. It tests cases where NUMPOINTS < 3,
     * cases where the condition is met, and cases where the condition is not met.
     */
    @Test
    void testLic7() {
        Parameters params = new Parameters();
        // case 1 NUMPOINTS < 3,  should return false
        params.LENGTH1 = 5;
        params.K_PTS = 1;
        int NUMPOINTS = 2; 
        Point[] points = new Point[] { new Point(1, 1), new Point(2, 2) };
        Declarations fewPoints = new Declarations(NUMPOINTS, points, params, null, null);
        assertFalse(fewPoints.compute_lic_7(), "LIC7 should return false when NUMPOINTS < 3");

        // case 2 valid case adn conditions, should return true
        params.LENGTH1 = 3;
        params.K_PTS = 1;
        NUMPOINTS = 5;
        points = new Point[] {new Point(1, 1),new Point(2, 2),new Point(3, 3),new Point(10, 10),new Point(4, 4)};
        Declarations valid = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(valid.compute_lic_7(), "LIC7 should return true when the condition is met");

        // case 3 valid case invalid conditions, should return false
        params.LENGTH1 = 10;
        params.K_PTS = 1;
        points = new Point[] {new Point(1, 1),new Point(2, 2),new Point(3, 3),new Point(4, 4),new Point(5, 5)};
        Declarations invalid = new Declarations(NUMPOINTS, points, params, null, null);
        assertFalse(invalid.compute_lic_7(), "LIC7 should return false when the condition is not met");

        // case 4 edge case, minimum K_PTS
        params.LENGTH1 = 2;
        params.K_PTS = 1; 
        points = new Point[] {new Point(0, 0),new Point(0, 0),new Point(3, 4)};
        Declarations edgeCaseMin = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(edgeCaseMin.compute_lic_7(), "LIC7 should return true for minimum K_PTS when condition is met");

        // case 5 edge case, maximum K_PTS
        params.LENGTH1 = 3;
        params.K_PTS = NUMPOINTS - 2;
        points = new Point[] {new Point(0, 0),new Point(1, 1), new Point(2, 2),new Point(3, 3),new Point(10, 10)};
        Declarations edgecaseMax = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(edgecaseMax.compute_lic_7(), "LIC7 should return true for maximum K_PTS when condition is met");
    }    
  

//-----------------------LIC2---------------------------------------------------------

    /**
     * Tests the “true” branch of the contract:
     * - We have at least 3 consecutive points forming an angle
     *   that is strictly less than (π - EPSILON) OR strictly greater than (π + EPSILON).
     */
    @Test
    void testLIC2True() {
        
        Point[] points = new Point[] {
            new Point(0, 0),
            new Point(1, 0),
            new Point(2, 1)
        };
        Parameters params = new Parameters();
        params.EPSILON = 0.1; // small enough so that angle < (π - 0.1)

        Declarations dec = new Declarations(3, points, params, null, null);

        // Assertion verifying the contract:
        // "Expected true because the angle at (1,0) is acute (i.e. < π - 0.1)."
        assertTrue(dec.compute_lic_2(), "Should return true for an acute angle < (π - EPSILON)."
        );
    }

    /**
     * Tests the “false” branch of the contract:
     * - Case A: NUMPOINTS < 3 → cannot possibly satisfy the condition.
     * - Case B: Three collinear points → angle is exactly π, which is
     *   neither < (π - EPSILON) nor > (π + EPSILON).
     */
    @Test
    void testLIC2False() {
        // Case A: Only 2 points
        Point[] twoPoints = new Point[] {
            new Point(0, 0),
            new Point(1, 1)
        };
        Parameters paramsFew = new Parameters();
        paramsFew.EPSILON = 0.5;
        Declarations decFew = new Declarations(2, twoPoints, paramsFew, null, null);

        // Assertion verifying the contract:
        // "Should return false if there are fewer than 3 points."
        assertFalse(decFew.compute_lic_2(), "Expected false due to insufficient points (no triple).");

        // Case B: Three collinear points → angle is π
        Point[] collinearPoints = new Point[] {
            new Point(0, 0),
            new Point(1, 0),
            new Point(2, 0)
        };
        Parameters paramsCollinear = new Parameters();
        paramsCollinear.EPSILON = 0.1;

        Declarations decCollinear = new Declarations(3, collinearPoints, paramsCollinear, null, null);

        // Assertion verifying the contract:
        // "Angle == π => does not satisfy < (π - EPSILON) or > (π + EPSILON)."
        assertFalse(decCollinear.compute_lic_2(),"Should return false for collinear points (angle = π)." );
    }

    /**
     * Tests “invalid input” according to the contract:
     * - The parameter EPSILON must satisfy 0 ≤ EPSILON < π.
     * - If EPSILON < 0 or EPSILON ≥ π, we treat it as invalid and expect false.
     */
    @Test
    void testLIC2InvalidInput() {
        Point[] points = {
            new Point(0, 0),
            new Point(1, 0),
            new Point(2, 1)
        };

        // EPSILON < 0 => invalid
        Parameters invalidParams1 = new Parameters();
        invalidParams1.EPSILON = -0.1;
        Declarations decInvalid1 = new Declarations(3, points, invalidParams1, null, null);
        assertFalse(
            decInvalid1.compute_lic_2(),
            "Should return false because EPSILON < 0 is invalid input."
        );

        // EPSILON >= π => invalid
        Parameters invalidParams2 = new Parameters();
        invalidParams2.EPSILON = Math.PI; // π itself is not allowed
        Declarations decInvalid2 = new Declarations(3, points, invalidParams2, null, null);
        assertFalse(
            decInvalid2.compute_lic_2(),
            "Should return false because EPSILON >= π is invalid input."
        );
    }

}
