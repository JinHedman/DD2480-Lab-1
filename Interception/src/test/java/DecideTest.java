
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

   
    
}
