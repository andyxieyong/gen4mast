package es.unican.istr.tests;

import es.unican.istr.gen4mast.tool.config.AnalysisOptions;
import es.unican.istr.gen4mast.tool.config.AssignmentOptions;
import es.unican.istr.gen4mast.tool.config.HOSPAConfig;
import es.unican.istr.gen4mast.tool.config.MastToolConfig;
import es.unican.istr.rtgen.tool.config.ToolConfig;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 21/01/2016.
 */
public class testToolConfig {

    public static void main(String[] args) {

        MastToolConfig m = new MastToolConfig();
        HOSPAConfig h = new HOSPAConfig();
        m.setName("Test");
        m.setWorkPath(".");
        //m.setMastPath("D:\\Development\\mast_svn\\mast_analysis\\exe\\mast_analysis.exe");
        m.setMastPath("D:\\Development\\MAST\\mast_svn\\mast_analysis\\exe\\mast_analysis.exe");
        m.setAnalysis(AnalysisOptions.OFFSET);
        m.setSync(true);
        m.setAssignment(AssignmentOptions.PD);
        m.setHospaConfig(h);
        m.setStopFactor(10.0f);
        m.setGsd(false);
        m.setDsFactor(1);
        m.setCalculateSlack(false);
        m.setJitterAvoidance(false);

        String[] params = m.getConfigAsArgs();
        for (String p: params) {
            System.out.println(p);
        }

        System.out.println("\n");

        ToolConfig m2 = new MastToolConfig();
        m2.initWihArgs(params);
        String[] params2 = m2.getConfigAsArgs();
        for (String p: params2) {
            System.out.println(p);
        }

    }

}
