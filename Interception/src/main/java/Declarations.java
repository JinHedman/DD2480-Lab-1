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
