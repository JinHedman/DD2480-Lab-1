
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
}
