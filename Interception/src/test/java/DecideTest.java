
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

   
    
}
