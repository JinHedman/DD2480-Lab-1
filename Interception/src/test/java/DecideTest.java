
package Interception.src.test.java; 

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;

import Interception.src.main.java.Decide;
import Interception.src.main.java.Parameters;
import Interception.src.main.java.Point;
import Interception.src.main.java.Declarations;

class DecideTest {
    

    /*
     * Function for testing if LIC0 returns true when it is supposed to, which means testing if ir returns true when there are
     * two consecutive points at a distance greater than length LENGTH1 from each other. 
     * It also tests if LIC0 returns false when it is supposed to, which is when there are no consecutive points at a distance greater than 
     * LENGTH1 from each other. 
     */ 
    @Test
    void testLic0(){
        Parameters params = new Parameters();
        params.LENGTH1 = 3; 
        int NUMPOINTS = 3;
        
        Point[] points = new Point[] {new Point(1, 5), new Point(5, 4), new Point(5, 6) };     
        
        Declarations trueDecide = new Declarations(NUMPOINTS, points, params, null, null);
       
        assertTrue(trueDecide.compute_lic_0());

        Point[] falsePoints = new Point[] {new Point(1, 2), new Point(2, 3), new Point(3, 4) };     
        
        Declarations falseDecide = new Declarations(NUMPOINTS, falsePoints, params, null, null);
        assertFalse(falseDecide.compute_lic_0());


    }

    /*
     * Function for testing if LIC returns the correct boolean value.   
     * Returns true when the three points can not be circumscribed by a circle with radius RADIUS1.
     * Returns false when the three points can be circumscribed by a circls with radius RADIUS1.
     */
    @Test 
    void testLic1(){
        Parameters params = new Parameters();
        params.RADIUS1 = 5;
        int NUMPOINTS = 3;


        // Should return true.
        Point[] points = new Point[] {new Point(0, 0), new Point(11, 0), new Point(10, 3) };     
        Declarations trueDecide = new Declarations(NUMPOINTS, points, params, null, null);
        assertTrue(trueDecide.compute_lic_1());

        // should return false.: a simple right angle triangle. it should not pass since the hypothenuse is 5.
        Point[] negativePoints = new Point[] {new Point(0, 0), new Point(3, 0), new Point(3, 4) };     
        Declarations falseDecide = new Declarations(NUMPOINTS, negativePoints, params, null, null);
        assertFalse(falseDecide.compute_lic_1());


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
    
}
