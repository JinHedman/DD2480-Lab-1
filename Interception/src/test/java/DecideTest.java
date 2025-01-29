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
     * Function for testing if LIC0 is true, which means testing if there are two points at a distance greater than length LENGTH1 from each other. 
     * It also tests if LIC0 returns false when it is supposed to, which is when there are no consequtive points at a distance greater than 
     * LENGTH1 from each other. 
     */ 
    @Test
    void testLic0True(){
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
  

    /*
     * Function for testing if LIC2 works as expected.
     * Returns true when the angle is less then PI minus epsilon or greater then PI + epsilon.
     * Returns false when the number of points is less than three, the points are colinear or when points coincide.
     */
    @Test 
    void testLic2() {
        // 1) Should return true if the angle at p2 is acute => angle < PI - EPS.
        Point[] points = new Point[] { new Point(0,0), new Point(1,0), new Point(2,1) };
        Parameters testParam = new Parameters();
        testParam.EPSILON = 0.1;  // a small epsilon
        Declarations dec = new Declarations(3, points, testParam, null, null);
        assertTrue(dec.compute_lic_2(),
            "Expected true because the angle at p2 is acute, so angle < PI - EPS.");
    
        // 2) Only 2 points => cannot form a triple => should return false
        Point[] pts = new Point[] { new Point(0,0), new Point(1,1) };
        Parameters paramsFalse = new Parameters();
        paramsFalse.EPSILON = 0.5;  
        Declarations declarations = new Declarations(2, pts, paramsFalse, null, null);
        assertFalse(declarations.compute_lic_2(),
            "Should return false if there are fewer than 3 points.");
    
        // 3) Three consecutive collinear points => angle at middle = PI => expect false
        Point[] pointColinear = new Point[] { new Point(0,0), new Point(1,0), new Point(2,0) };
        Parameters paramsColinear = new Parameters();
        paramsColinear.EPSILON = 0.1;  // must set on paramsColinear
        Declarations decColinear = new Declarations(3, pointColinear, paramsColinear, null, null);
    
        assertFalse(decColinear.compute_lic_2(),
            "Angle is exactly PI => not < PI - EPS, not > PI + EPS => should be false.");
    } 
}


