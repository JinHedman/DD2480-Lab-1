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

        boolean[] CMV = new boolean[15];
        CMV[0] = declarations.compute_lic_0();
        CMV[1] = declarations.compute_lic_1();
        CMV[2] = declarations.compute_lic_2();
        CMV[3] = declarations.compute_lic_3();
        CMV[4] = declarations.compute_lic_4();
        CMV[5] = declarations.compute_lic_5();
        CMV[6] = declarations.compute_lic_6();
        CMV[7] = declarations.compute_lic_7();
        CMV[8] = declarations.compute_lic_8();
        CMV[9] = declarations.compute_lic_9();
        CMV[10] = declarations.compute_lic_10();
        CMV[11] = declarations.compute_lic_11();
        CMV[12] = declarations.compute_lic_12();
        CMV[13] = declarations.compute_lic_13();
        CMV[14] = declarations.compute_lic_14();
        declarations.setCMV(CMV);

        
        // Compute FUV from PUM and PUV
        boolean[][] pum = declarations.compute_pum();
        declarations.genFUV(pum, declarations.getPUV());

        // Final decision (launch iff all FUV entries are true)
        for (boolean entry : declarations.getFUV()) {
            if (!entry) return false;
        }
        return true;
    }
}
