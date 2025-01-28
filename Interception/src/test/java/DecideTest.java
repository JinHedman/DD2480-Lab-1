
package Interception.src.test.java; 

// this is solved at compile time if nothing else. 
import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;

import Interception.src.main.java.Decide;
import Interception.src.main.java.Parameters;
import Interception.src.main.java.Point;
import Interception.src.main.java.Declarations;

class DecideTest {

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
}
