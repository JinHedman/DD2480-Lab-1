package Interception.src.main.java;

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
