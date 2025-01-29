package Interception.src.main.java;

import java.lang.reflect.Parameter;
import java.util.List;

import Interception.src.main.java.Declarations;
    
public class Decide {

    private int numpoints;
    private Point[] points;
    private Parameters Parameters;
    private Declarations.Connectors[][] LCM;
    private boolean[] PUV;

    public Decide(int numpoints, Point[] points, Parameters Parameters, Declarations.Connectors[][] LCM, boolean[] PUV) {
        this.numpoints = numpoints;
        this.points = points;
        this.Parameters = Parameters;
        this.LCM = LCM;
        this.PUV = PUV;
    }

    public  boolean decide() {
        Declarations declarations = new Declarations(numpoints, points, Parameters, LCM, PUV);
        boolean[][] pum = declarations.compute_pum();

        // Compute FUV from PUM and PUV
        declarations.genFUV(pum, declarations.getPUV());

        // Final decision (launch iff all FUV entries are true)
        for (boolean entry : declarations.getFUV()) {
            if (!entry) return false;
        }
        return true;
    }
}
