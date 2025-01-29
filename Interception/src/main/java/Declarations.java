package Interception.src.main.java;

import java.util.List;

public class Declarations {
    private static final double PI = 3.1415926535; 
    public final int NUMPOINTS; 
    private Point[] points;

    // hard-coded arrays
    private boolean[] CMV = new boolean[15]; // Conditions Met Vector
    private boolean[][] PUM = new boolean[15][15]; // Preliminary Unlocking Matrix
    private boolean[] FUV = new boolean[15]; // Final Unlocking Vector

    // For these, we accept them from outside
    private Connectors[][] LCM; // Logical Connector Matrix
    private boolean[] PUV; // Preliminary Unlocking Vector

    // enum CONNECTORS for LCM array
    public enum Connectors {
        NOTUSED(777),
        ORR(-1),  
        ANDD(-1);

        private final int value;

        Connectors(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // enum COMPTYPE
    public enum CompType {
        LT(1111), // less than
        EQ(-1),   // equal
        GT(-1);   // greater than

        private final int value;

        CompType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Parameters params;

    // constructor
    public Declarations(int NUMPOINTS, 
                        Point[] points, 
                        Parameters params, 
                        Connectors[][] LCM, 
                        boolean[] PUV) {
        this.NUMPOINTS = NUMPOINTS;  // final
        this.points = points;
        this.params = params;
        this.LCM = LCM;
        this.PUV = PUV;
    } 


    /*
     * Code for the assertion that two consequitive points are a 
     */
    public boolean compute_lic_0(){
        for(int i=1; i < NUMPOINTS; i++){
            if(points[i].distance(points[i-1]) > params.LENGTH1){
                return true;
            }
        }
        return false;
    }

    // Need to return the smallest possible circle 
    public boolean compute_lic_1(){
        for(int i = 2; i< NUMPOINTS; i++){
            double a = points[i-2].distance(points[i]);  
            double b = points[i-1].distance(points[i]);           
            double c = points[i-1].distance(points[i-2]);    
            
            // if any of the points are a distance greater then 2 *params.R can we return immediately.
            if (a > 2*params.RADIUS1 || b >  2*params.RADIUS1 || c >  2*params.RADIUS1){
                // We found a triple that does NOT fit => return true 
                return true;
            }
            double area = heronsRule(a,b,c);
            // Need the longest side.
            double longest = Math.max(a, Math.max(b, c));
            double neededRadius;
            // If area is zero are they colinear and we compute half the distance of the longestt
            if(area == 0){
                neededRadius = longest / 2.0;
            }else{
                /*
                 * Here do we have two cases. the neededRadius = (a * b * c) / (4.0 * area) formula is the 
                 * formula that passes through the vertices which is correct for sharp anges, but with obstuse/right angles is the longest side the diameter. 
                 * There is also a special case for when all are colinear. 
                 */
                double x, y;
                if (longest == a) {
                    x = b; y = c;
                } else if (longest == b) {
                    x = a; y = c;
                } else {
                    x = a; y = b;
                }
                // right/obtuse if longest^2 >= x^2 + y^2
                if (longest*longest >= x*x + y*y) {
                    // The minimal circle is diameter = "longest"
                    neededRadius = longest / 2.0;
                } else {
                    // acute => use circumcircle radius: R = (abc)/(4 * area)
                    neededRadius = (a * b * c) / (4.0 * area);
                }
            }
            // return true if the radius is greater. 
            if (neededRadius > params.RADIUS1) {
                return true; 
            }
        }
        return false; 
    }

    /*
     *  Computes LIC13, returns true if there are three consequtive points with A_PTS and B_PTS between, respectively, outside RADIUS1
     *  and if there are three consequtive points with A_PTS and B_PTS between, respectively, inside or on RADIUS2.
     */
    public boolean compute_lic_13() {
        if (NUMPOINTS < 5) {
            return false;
        }
        
        int maxI = NUMPOINTS - (params.A_PTS + params.B_PTS + 3);

        if (maxI < 0) {
            return false;
        }
        
        boolean condition1 = false;
        boolean condition2 = false;
        
        for (int i = 0; i <= maxI; i++) {
            int j = i + params.A_PTS + 1;
            int k = j + params.B_PTS + 1;
            
            double diff_ij = points[i].distance(points[j]);
            double diff_ik = points[j].distance(points[k]);
            double diff_jk = points[k].distance(points[k]);
            
            // Find longest side 'c' and other sides 'a' and 'b'
            double a, b, c;
            if (diff_ij >= diff_ik && diff_ij >= diff_jk) {
                c = diff_ij;
                a = diff_ik;
                b = diff_jk;
            } else if (diff_ik >= diff_ij && diff_ik >= diff_jk) {
                c = diff_ik;
                a = diff_ij;
                b = diff_jk;
            } else {
                c = diff_jk;
                a = diff_ij;
                b = diff_ik;
            }
            
            double radius;
            if (c == 0) {
                radius = 0;  // All points are identical
            } else {
                double area = heronsRule(a, b, c);
                
                if (Double.isNaN(area) || area <= 1e-12) {  // Collinear case
                    radius = c / 2;
                } else if (c * c > a * a + b * b + 1e-12) {  // Obtuse triangle
                    radius = c / 2;
                } else {  // Acute triangle (use circumradius)
                    radius = (a * b * c) / (4 * area);
                }
            }
            
            if (radius > params.RADIUS1) {
                condition1 = true;
            }
            if (radius <= params.RADIUS2) {
                condition2 = true;
            }
            
            if (condition1 && condition2) {
                return true;
            } 
                
        }
        
        return condition1 && condition2;
    }


    /*
     * Help function for calculating the area of a triangle given its three sides.
     */
    public double heronsRule(double a, double b, double c){
        double s = (a+b+c)/2;
        double A = Math.sqrt((s*(s-a)*(s-b)*(s-c)));
        return A; 
    }


    // test code
    public static void main(String[] args) {
        System.out.println("PI: "+PI);

        Connectors connector = Connectors.NOTUSED;
        System.out.println("Connector Value: "+connector.getValue());

        // constructor ex
        Point[] points = new Point[100]; 
        Parameters params = new Parameters();
        Connectors[][] LCM = new Connectors[15][15];
        boolean[] PUV = new boolean[15];

        Declarations declarations = new Declarations(100, points, params, LCM, PUV);
        System.out.println("NUMPOINTS: "+declarations.NUMPOINTS);
    }
}
