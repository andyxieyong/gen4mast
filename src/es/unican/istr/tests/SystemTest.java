package es.unican.istr.tests;

import es.unican.istr.gen4mast.system.MastFlow;
import es.unican.istr.gen4mast.system.MastProcessor;
import es.unican.istr.gen4mast.system.MastSystem;
import es.unican.istr.gen4mast.system.MastTask;
import es.unican.istr.gen4mast.tool.MastTool;
import es.unican.istr.gen4mast.tool.config.AnalysisOptions;
import es.unican.istr.gen4mast.tool.config.AssignmentOptions;
import es.unican.istr.gen4mast.tool.config.HOSPAConfig;
import es.unican.istr.gen4mast.tool.config.MastToolConfig;
import es.unican.istr.rtgen.exceptions.InterruptedAnalysis;
import es.unican.istr.rtgen.system.config.SystemConfig;
import es.unican.istr.rtgen.system.config.deadline.DeadlineConfig;
import es.unican.istr.rtgen.system.config.load.LoadBalancingOptions;
import es.unican.istr.rtgen.system.config.load.UtilizationConfig;
import es.unican.istr.rtgen.system.config.load.WCETGenerationOptions;
import es.unican.istr.rtgen.system.config.localization.LocalizationOptions;
import es.unican.istr.rtgen.system.config.period.PeriodConfig;
import es.unican.istr.rtgen.system.config.period.PeriodDistributionOptions;

/**
 * Created by juanm on 13/10/2015.
 */
public class SystemTest {

    public static void main(String[] args) {

        PeriodConfig period = new PeriodConfig(PeriodDistributionOptions.LOG_UNIFORM,100.0f,100.0f);
        DeadlineConfig deadline = new DeadlineConfig("NT");
        UtilizationConfig utilization = new UtilizationConfig(52, 0.0f, WCETGenerationOptions.UUNIFAST, LoadBalancingOptions.BALANCED);
        SystemConfig s = new SystemConfig(4,20,10,10,false,0.0f,"EDF",period,deadline, LocalizationOptions.AVOID_REPETITION,utilization);
        MastSystem<MastTask, MastFlow, MastProcessor> sys = new MastSystem(s);
        System.out.println(sys.getSystemUtilization());
        sys.printOverview();


        MastToolConfig m = new MastToolConfig();
        HOSPAConfig h = new HOSPAConfig();
        m.setName("Test");
        m.setWorkPath(".");
        //m.setMastPath("D:\\Development\\mast_svn\\mast_analysis\\exe\\mast_analysis.exe");
        m.setMastPath("D:\\Development\\MAST\\mast_svn\\mast_analysis\\exe\\mast_analysis.exe");
        m.setAnalysis(AnalysisOptions.OFFSET);
        m.setSync(false);
        m.setAssignment(AssignmentOptions.PD);
        m.setHospaConfig(h);
        m.setStopFactor(1.0f);
        m.setGsd(false);
        m.setDsFactor(1);
        m.setCalculateSlack(false);
        m.setJitterAvoidance(false);

        MastTool t = new MastTool("1");
        try {
            t.analyze(sys, m);
        } catch (InterruptedAnalysis interruptedAnalysis) {
            interruptedAnalysis.printStackTrace();
        }

        System.out.println(sys.isSchedulable());
    }

}
