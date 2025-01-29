
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
     * Function for testing if LIC3 returns the correct boolean values.
     * Returns true when the three points
     * Returns false when there are less than three points, the points can not form a triangle, the area is negative or the area is less than AREA1.
     */
    @Test
    void testLIC3(){

        // should return true when there is a triangle with area greater than AREA1.
        Parameters params = new Parameters();
        
        params.AREA1 = 1.0;
        int NUMPOINTS=  5;
        Point [] truePoints = new Point[] { new Point(0, 0),new Point(2, 0), new Point(0, 2), new Point(1, 1), new Point(3, 3)  };
        Declarations trueDecide = new Declarations(NUMPOINTS, truePoints, params, null, null);
        assertTrue(trueDecide.compute_lic_3(), "Should return true when at least one triangle has area greater than AREA1.");
    

        // Test that it returns false if the number of points is less than three.
        params.AREA1 = 1.0;
        NUMPOINTS = 2;
        Point[] toFewPoints = new Point[] { new Point(0, 0),new Point(1, 1)};
        Declarations falseDecide = new Declarations(NUMPOINTS, toFewPoints, params, null, null);
        assertFalse(falseDecide.compute_lic_3(), "Should return false when less than three points are provided.");

        //Should return false when area is negative.
        params.AREA1 = -1.0;
        NUMPOINTS = 3;
        Point[] negativePoints = new Point[] {new Point(0, 0),new Point(1, 0), new Point(0, 1)};
        Declarations negativeArea = new Declarations(NUMPOINTS, negativePoints, params, null, null);
        assertFalse(negativeArea.compute_lic_3(), "Should return false when less than three points are provided.");

        // Should return false when the triangle is degenerate. 
        params.AREA1 = 1;
        NUMPOINTS = 4;
        Point[] degenPoints = new Point[] {new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3)};
        Declarations degenArea = new Declarations(NUMPOINTS, degenPoints, params, null, null);
        assertFalse(degenArea.compute_lic_3(), "Should return false when AREA1 is zero but all triangles are degenerate.");
    

        // Shoule return false when there is no triangle with area greater than AREA1.
        params.AREA1 = 1.0;
        NUMPOINTS = 5; 
        Point[] points = new Point[] {new Point(0, 0),new Point(1, 0), new Point(1, 1),new Point(2, 1), new Point(3, 1)};
        Declarations smallArea = new Declarations(NUMPOINTS, points, params, null, null);
        assertFalse(smallArea.compute_lic_3(), "Should return false when no triangle has area greater than AREA1.");
        
        

    }

    
}
