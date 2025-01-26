package Interception.src.main;

public class Declarations {
    public static final double PI = 3.1415926535; 

    // enum CONNECTORS
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

    public static class Coordinate {
        public double[] points = new double[100]; // pointer to an array of 100 doubles
    }

    public static class CMatrix {
        public Connectors[][] matrix = new Connectors[15][15]; //pointer to a 2-D array of [15,15] CONNECTORS
    }

    public static class BMatrix {
        public boolean[][] matrix = new boolean[15][15]; // pointer to a 2-D array of [15,15] booleans
        // might need to convert input if input is a array of 0s and 1s.
    }

    public static class Vector {
        public boolean[] vector = new boolean[15]; // pointer to an array of 15 booleans
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

    // test code
    public static void main(String[] args) {
        System.out.println("PI: " + PI);

        Connectors connector = Connectors.NOTUSED;
        System.out.println("Connector Value: " + connector.getValue());

        Coordinate coordinate = new Coordinate();
        coordinate.points[0] = 1.0;
        coordinate.points[1] = 2.0;
        System.out.println("First coordinate point: " + coordinate.points[0]);

        BMatrix bMatrix = new BMatrix();
        bMatrix.matrix[0][0] = true;
        System.out.println("First value in BMatrix: " + bMatrix.matrix[0][0]);

        Vector vector = new Vector();
        vector.vector[0] = true;
        System.out.println("First value in Vector: " + vector.vector[0]);
    }
}
