
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
   
    
}




