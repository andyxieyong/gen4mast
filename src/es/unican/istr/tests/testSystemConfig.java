package es.unican.istr.tests;

import es.unican.istr.rtgen.system.config.SystemConfig;
import es.unican.istr.rtgen.system.config.deadline.DeadlineConfig;
import es.unican.istr.rtgen.system.config.load.LoadBalancingOptions;
import es.unican.istr.rtgen.system.config.load.UtilizationConfig;
import es.unican.istr.rtgen.system.config.load.WCETGenerationOptions;
import es.unican.istr.rtgen.system.config.localization.LocalizationOptions;
import es.unican.istr.rtgen.system.config.period.PeriodConfig;
import es.unican.istr.rtgen.system.config.period.PeriodDistributionOptions;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 21/01/2016.
 */
public class testSystemConfig {

    public static void main(String[] args) {

        PeriodConfig period = new PeriodConfig(PeriodDistributionOptions.LOG_UNIFORM,100.0f,100.0f);
        DeadlineConfig deadline = new DeadlineConfig("T");
        UtilizationConfig utilization = new UtilizationConfig(10, 0.0f, WCETGenerationOptions.SCALE, LoadBalancingOptions.BALANCED);
        utilization.setUtilization(10);
        SystemConfig s = new SystemConfig(1000,5,2,5,false,0.0f,"FP",period,deadline, LocalizationOptions.RANDOM,utilization);

        String[] params = s.getConfigAsArgs();
        for (String p: params){
            System.out.println(p);
        }

        System.out.println("\n");

        SystemConfig s2 = new SystemConfig();
        s2.initWihArgs(params);
        String[] params2 = s2.getConfigAsArgs();
        for (String p : params2){
            System.out.println(p);
        }

    }

}
