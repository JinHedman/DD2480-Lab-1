
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
   
    
}




