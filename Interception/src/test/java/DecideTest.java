
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
}