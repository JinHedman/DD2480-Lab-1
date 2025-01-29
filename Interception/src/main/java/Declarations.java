package Interception.src.main.java;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

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
     * Function for populating the pum matrix based on on the values in the 
     * 
     */
    public boolean[][] compute_pum() {
      
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                // Check for null values and return false 
                if(LCM[i][j] == null){
                    // can not return false so do a exception. 
                    throw new NullPointerException("LCM contains null connector at LCM[" + i + "][" + j + "] or LCM[" + j + "][" + i + "].");
                }
                switch (LCM[i][j]) {
                    case NOTUSED:
                        PUM[i][j] = true;
                        break;
                    case ANDD:
                        PUM[i][j] = CMV[i] && CMV[j];
                        break;
                    case ORR:
                        PUM[i][j] = CMV[i] || CMV[j];
                        break;
                    default:
                        // if there is an issue with the connectors do we return false.
                        PUM[i][j] = false; 
                }
            }
        }
        return PUM;
    }

    /*
     * Code for the assertion that there exists two points that are K_PTS apart and atleast one of them have
     * distance greater than LENGTH1 and atleast one of them have distance smaller than LENGTH2
     */
    public boolean compute_lic_12() {
        if (NUMPOINTS < 3) {
            return false;
        }
        if (params.K_PTS <= 0) {
            return false;
        }
        if (params.LENGTH2 < 0) {
            return false;
        }
        // Calculate the maximum index i such that i + K_PTS + 1 < numPoints
        int maxI = NUMPOINTS - params.K_PTS - 2;
        if (maxI < 0) {
            return false; // No valid pairs possible
        }
        boolean part1 = false;
        boolean part2 = false;
        for (int i = 0; i <= maxI; i++) {
            int j = i + params.K_PTS + 1;
            double dx = points[j].getX() - points[i].getX();
            double dy = points[j].getY() - points[i].getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance > params.LENGTH1) {
                part1 = true;
            }
            if (distance < params.LENGTH2) {
                part2 = true;
            }
            if (part1 && part2) {
                break;
            }
        }
        return part1 && part2;
    }


    /*
     * Code for the assertion that two consequitive points are a 
     */
    public boolean compute_lic_0(){
        if(NUMPOINTS < 2 || params.LENGTH1 < 0){
            return false; 
        }
        for(int i=1; i < NUMPOINTS; i++){
            if(points[i].distance(points[i-1]) > params.LENGTH1){
                return true;
            }
        }
        return false;
    }


    public boolean compute_lic_8() {
        for (int i = 0; i < NUMPOINTS - (params.A_PTS + 1) - (params.B_PTS + 1); i++) {
            Point a = points[i];
            Point b = points[i + (params.A_PTS + 1)];
            Point c = points[i + (params.A_PTS + 1) + (params.B_PTS + 1)];
            System.out.println("Distance from a to b: " + a.distance(b));
            System.out.println("Distance from a to c: " + a.distance(c));
            System.out.println("Distance from b to c: " + b.distance(c));
            if (a.distance(b) <= 2 * params.RADIUS1 &&
                a.distance(c) <= 2 * params.RADIUS1 &&
                b.distance(c) <= 2 * params.RADIUS1) {
                return false;
            }
        }
        return true;
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
  
    public boolean compute_lic_14() {
        boolean greaterThanArea1 = false;
        boolean lessThanArea2 = false;
        for (int i = 0; i < NUMPOINTS - (params.E_PTS + 1) - (params.F_PTS + 1); i++) {
            double a = points[i].distance(points[i + (params.E_PTS + 1)]); 
            double b = points[i].distance(points[i + (params.E_PTS + 1) + (params.F_PTS + 1)]);
            double c = points[i + (params.E_PTS + 1)].distance(points[i + (params.E_PTS + 1) + (params.F_PTS + 1)]);

            double area = heronsRule(a, b, c);
            if (area > params.AREA1) {
                greaterThanArea1 = true;
            }
            if (area < params.AREA2) {
                lessThanArea2 = true;
            }
            if (greaterThanArea1 && lessThanArea2) {
                return true;
            }
        }
        return false;
    }
  
    /*
     * Code to assert LIC11, if there exists two points seperated by G_PTS consecutive intervening points in bounds.
     * And that NUMPOINTS < 3, 1<= G_PTS <= NUMPOINTS-2.
    */
    public boolean compute_lic_11(){
        if(NUMPOINTS<3){
            return false;
        }
        // asses G_PTS
        if(params.G_PTS<1 || params.G_PTS>NUMPOINTS-2){
            return false;
        }

        for(int i=0;i<=NUMPOINTS-params.G_PTS-2;i++){
            // index for point which is G_PTS aways from point with index i 
            int j = i+params.G_PTS+1;
            // check bounds
            if(j>=NUMPOINTS){
                continue;
            }

            // check if points meet condtion
            if(points[j].x<points[i].x){
                return true;
            }
        }
        return false;
    }
  
    /*
     * Code for asserting if the distance between two points are greater than LENGTH1
     */
    public boolean compute_lic_7(){
        // the number of numpoints are too few
        if(NUMPOINTS < 3){
            return false;
        }
        for(int i = 0; i <= NUMPOINTS-params.K_PTS-2;i++){
            int j = i + params.K_PTS+1;
            // out of bounds check
            if(j>=NUMPOINTS){
                continue;
            }
            if(points[i].distance(points[j])>params.LENGTH1){
                return true;
            }  
        }
        // no pair fits the condition
        return false; 
      }

    public boolean compute_lic_2(){
            double EPS = params.EPSILON;      
            if (NUMPOINTS < 3) return false;
            for (int i = 1; i < NUMPOINTS - 1; i++) {
                Point p1 = points[i - 1];
                Point p2 = points[i];    
                Point p3 = points[i + 1];
                // Skip if p2 coincides with p1 or p2 coincides with p3 => angle undefined
                if ((p2.getX() == p1.getX() && p2.getY() == p1.getY()) ||
                    (p2.getX() == p3.getX() && p2.getY() == p3.getY())) {
                    continue;
                }
                double b = p1.distance(p2);  
                double a = p2.distance(p3);  
                double c = p1.distance(p3);  
                double cosVal = (a*a + b*b - c*c) / (2.0 * a * b);            
                // Clamp cosVal to [-1, 1]
              //  if (cosVal > 1.0) {   cosVal = 1.0;}
               // if (cosVal < -1.0) { cosVal = -1.0;}
        
                double angle = Math.acos(cosVal);
 
                if (angle < (PI - EPS) || angle > (PI + EPS)) {
                    return true;
                }
            }        
            return false;
    }

    public boolean compute_lic_6() {
        for (int i = 0; i <= points.length - params.N_PTS; i++) {
            Point startPoint = points[i];
            Point endPoint = points[i + params.N_PTS - 1];

            for (int j = 1; j < params.N_PTS - 1; j++) {
                Point currentPoint = points[i + j];
                double distance;

                if (startPoint == endPoint) {
                    distance = startPoint.distance(currentPoint);
                } else {
                    distance = pointToLineDistance(startPoint, endPoint, currentPoint);
                }
                if (distance > params.DIST) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean compute_lic_10() {
        for (int i = 0; i < NUMPOINTS - (params.E_PTS + 1) - (params.F_PTS + 1); i++) {
            double a = points[i].distance(points[i + (params.E_PTS + 1)]); 
            double b = points[i].distance(points[i + (params.E_PTS + 1) + (params.F_PTS + 1)]);
            double c = points[i + (params.E_PTS + 1)].distance(points[i + (params.E_PTS + 1) + (params.F_PTS + 1)]);

            double area = heronsRule(a, b, c);
            if (area > params.AREA1) {
                return true;
            }
        }
        return false;
    }

    

    public boolean compute_lic_3(){
        if(params.AREA1 < 0){
            return false;
        }
        // Need to have at least three different points.
        if(points.length < 3){
            return false; 
        }
        for(int i = 2; i< points.length; i++){
                double a = points[i-2].distance(points[i]);  
                double b = points[i-1].distance(points[i]);           
                double c = points[i-1].distance(points[i-2]);  
    
                if(isValidTriangle(a,b,c)){
                    double Area = heronsRule(a,b,c); 
                    if (Area > params.AREA1){
                        return true; 
                    }
                }
            }
        return false;     
    }


    

    /*
     * Helper function to determine if it is a valid triangle with the triangle inequality.
     * 
     */
    private boolean isValidTriangle(double a, double b, double c){
        return (a + b > c) && (a + c > b) && (b + c > a);
    }

    /*
     * LIC 9
     * Code for asserting the angle of three data points 
     */
    public boolean compute_lic_9(){
        if(NUMPOINTS<5){
            return false;
        }
        // ensure valid parameters
        if(params.C_PTS<1 || params.D_PTS<1 || params.C_PTS+params.D_PTS > NUMPOINTS-3){
            return false;
        }
        for(int i = 0; i < NUMPOINTS - params.C_PTS-params.D_PTS-2;i++){
            int first = i;
            int second = i + params.C_PTS+1;
            int third = second + params.D_PTS+1;

            // check if within bounds
            if(third >= NUMPOINTS){
                continue;
            }

            Point A = points[first];
            Point B = points[second];
            Point C = points[third];

            // check if either the first or third point coincides with the second point, if so skip
            if((A.x == B.x && A.y == B.y) || (C.x == B.x && C.y == B.y)){
                continue;
            }
            double angle = calcAngle(A, B, C);

            // check if angle satisfies conditon
            if(angle < (Math.PI-params.EPSILON) || angle>(Math.PI+params.EPSILON)){
                return true;
            }
        }
        // default
        return false;
    }

    public boolean compute_lic_4(){
        int Q_PTS = params.Q_PTS;
        int QUADS = params.QUADS;

        // guard checks
        if (Q_PTS < 2 || Q_PTS > NUMPOINTS) {
            return false;
        }
        if (QUADS < 1 || QUADS > 3) {
            return false;
        }

        for (int start = 0; start <= NUMPOINTS - Q_PTS; start++) {
            // HashSet only contains unique elements.
            Set<Integer> distinctQuads = new HashSet<>();
            for (int j = 0; j < Q_PTS; j++) {
                Point p = points[start + j];
                distinctQuads.add(getQuadrant(p));
                if (distinctQuads.size() > QUADS) {
                    return true;
                }
            }
        }
        return false;
    }


    /*
     * Uses priory order to place them in the correct quadrant. 
     */
    private int getQuadrant(Point p) {
        double x = p.getX();
        double y = p.getY();
        // Quadrant I
        if (x >= 0 && y >= 0) return 1;
        // Quadrant II
        else if (x <= 0 && y >= 0) return 2;
        // Quadrant III
        else if (x <= 0 && y <= 0) return 3;
        // Quadrant IV
        else return 4;
    }

    /*
     * Code for LIC5.
     */
    public boolean compute_lic_5(){
        // If NUMPOINTS is less than two should it fail.
        if(NUMPOINTS < 2){
            return false; 
        }
        for(int i=1; i < NUMPOINTS; i++){
            if( ((points[i]).getX() - points[i-1].getX()) < 0) { 
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

    /*
     * Help function for calculating the distance from a given point, to a given straight line which goes through the start and endpoint.
     */
    private static double pointToLineDistance(Point lineStart, Point lineEnd, Point point) {
        double x0 = point.getX(), y0 = point.getY();
        double x1 = lineStart.getX(), y1 = lineStart.getY();
        double x2 = lineEnd.getX(), y2 = lineEnd.getY();

        double numerator = Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1);
        double denominator = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        if (denominator == 0) {
            return 0;
        } else {
            return numerator / denominator;
        }
    }

    /* 
     * Help function for calculating the angle of three given dots using vector dot product.
     */
    private double calcAngle(Point A, Point B, Point C){
        double BA_x = A.x - B.x;
        double BA_y = A.y - B.y;
        double BC_x = C.x - B.x;
        double BC_y = C.y - B.y;

        double dotPro = (BA_x * BC_x) + (BA_y * BC_y);

        double magBA = Math.sqrt(BA_x * BA_x + BA_y * BA_y);
        double magBC = Math.sqrt(BC_x * BC_x + BC_y * BC_y);

        // check for zero division
        if(magBA == 0 || magBC == 0){
            return Math.PI;
        }

        // compute angle
        double cosThe = dotPro / (magBA*magBC);
        // clamp cosThe to valid range
        cosThe = Math.max(-1.0,Math.min(1.0, cosThe));

        return Math.acos(cosThe);
    }

    /* 
    * Computes FUV based on PUM and PUV
    */
    public boolean[] genFUV(boolean[][] PUM, boolean[] PUV){
        int size = PUV.length;
        boolean[] FUV = new boolean[size];
        // if PUV[i] is false, set FUV[i] to true
        for(int i=0;i<size;i++){
            if(!PUV[i]){
                FUV[i] = true;
            }else{
                // else check if all values in PUM[i] are true
                boolean rowTrue = true;
                // ignore diagonals
                for(int j=0;j<size;j++){
                    if(i!=j && !PUM[i][j]){
                        rowTrue = false;
                        break;
                    }
                }
                FUV[i] = rowTrue;
            }
        }
        return FUV;
    }
    // getter function for pu
    public boolean[]  getCMV(){
        return CMV;
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
