package es.unican.istr.tests;

import es.unican.istr.gen4mast.storers.SQLiteMastUtilizationGeneratorStorer;
import es.unican.istr.gen4mast.system.MastSystem;
import es.unican.istr.gen4mast.tool.MastTool;
import es.unican.istr.gen4mast.tool.config.AnalysisOptions;
import es.unican.istr.gen4mast.tool.config.AssignmentOptions;
import es.unican.istr.gen4mast.tool.config.HOSPAConfig;
import es.unican.istr.gen4mast.tool.config.MastToolConfig;
import es.unican.istr.rtgen.generators.config.GeneratorConfig;
import es.unican.istr.rtgen.generators.config.UtilizationGeneratorConfig;
import es.unican.istr.rtgen.generators.UtilizationGenerator;
import es.unican.istr.rtgen.storers.config.StorerConfig;
import es.unican.istr.rtgen.system.config.SystemConfig;
import es.unican.istr.rtgen.system.config.deadline.DeadlineConfig;
import es.unican.istr.rtgen.system.config.load.LoadBalancingOptions;
import es.unican.istr.rtgen.system.config.load.UtilizationConfig;
import es.unican.istr.rtgen.system.config.load.WCETGenerationOptions;
import es.unican.istr.rtgen.system.config.localization.LocalizationOptions;
import es.unican.istr.rtgen.system.config.period.PeriodConfig;
import es.unican.istr.rtgen.system.config.period.PeriodDistributionOptions;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public class UtilizationSeriesTest {

    public static void main(String[] args) {

        // SystemConfig
        PeriodConfig period = new PeriodConfig(PeriodDistributionOptions.UNIFORM,100.0f,100.0f);
        DeadlineConfig deadline = new DeadlineConfig("T");
        UtilizationConfig utilization = new UtilizationConfig(10,0.0f,WCETGenerationOptions.SCALE,LoadBalancingOptions.BALANCED);
        SystemConfig s = new SystemConfig(1000,5,2,5,false,0.0f,"FP",period,deadline,LocalizationOptions.RANDOM,utilization);


        // MastConfig
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


        // Results Handler
        StorerConfig storerConfig = new StorerConfig();
        SQLiteMastUtilizationGeneratorStorer res = new SQLiteMastUtilizationGeneratorStorer(storerConfig);


        // Config Handler
        GeneratorConfig config = new UtilizationGeneratorConfig();


        // Instantiate and configure Utilization Series
        UtilizationGenerator<MastSystem, MastTool> series = new UtilizationGenerator<>(
                s, m, res, config);
        series.setLinearSystem(MastSystem.class);
        series.setRTTool(MastTool.class);


        // Run series
        series.run();


    }

}
