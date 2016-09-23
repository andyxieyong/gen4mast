package es.unican.istr.gen4mast;

import es.unican.istr.gen4mast.config.Gen4MASTConfig;
import es.unican.istr.gen4mast.dispatchers.config.MastClusterDispatcherConfig;
import es.unican.istr.gen4mast.dispatchers.config.MastLocalDispatcherConfig;
import es.unican.istr.gen4mast.storers.SQLiteMastUtilizationGeneratorResultsManager;
import es.unican.istr.gen4mast.system.MastSystem;
import es.unican.istr.gen4mast.tool.MastTool;
import es.unican.istr.rtgen.Dispatcher;
import es.unican.istr.rtgen.ResultsManager;
import es.unican.istr.rtgen.dispatchers.ClusterTorqueDispatcher;
import es.unican.istr.rtgen.dispatchers.LocalDispatcher;
import es.unican.istr.rtgen.dispatchers.config.DispatcherConfig;
import es.unican.istr.rtgen.evaluationengines.UtilizationEvaluationEngine;
import es.unican.istr.rtgen.evaluationengines.config.GeneratorConfig;
import es.unican.istr.rtgen.evaluationengines.config.UtilizationGeneratorConfig;
import es.unican.istr.rtgen.resultsmanagers.config.StorerConfig;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 08/10/2015.
 */

public class Main {

    public static void main(String[] args) {

        System.out.println("Gen4MAST");

        //////////////////////////////////
        // Load Main Configuration File //
        //////////////////////////////////

        String configFilePath = null;
        if (args.length == 0){
            configFilePath = "gen4mast.properties";
        } else {
            configFilePath = args[0];
        }
        System.out.printf("Loading configuration from %s\n", configFilePath);
        Gen4MASTConfig configMain = new Gen4MASTConfig(configFilePath);


        ///////////////////////////
        // ResultsManager Initialization //
        ///////////////////////////

        StorerConfig storerConfig = null;
        ResultsManager resultsManager = null;
        switch (configMain.getStorer()){
            case SQLITE_MAST:
                storerConfig = new StorerConfig(configFilePath);
                resultsManager = new SQLiteMastUtilizationGeneratorResultsManager(storerConfig);
                break;
        }


        //////////////////////////////
        // EvaluationEngine Initialization //
        //////////////////////////////

        GeneratorConfig generatorConfig = null;
        switch (configMain.getGenerator()){
            case UTILIZATION_SERIES:
                generatorConfig = new UtilizationGeneratorConfig(configFilePath);
        }


        ///////////////////////////////
        // Dispatcher Initialization //
        ///////////////////////////////

        DispatcherConfig dispatcherConfig = null;
        Dispatcher dispatcher = null;
        switch (configMain.getDispatcher()){
            case LOCAL:
                dispatcherConfig = new MastLocalDispatcherConfig(configFilePath);
                dispatcher = new LocalDispatcher<>(dispatcherConfig);
                break;
            case CLUSTER_TORQUE:
                dispatcherConfig = new MastClusterDispatcherConfig(configFilePath);
                dispatcher = new ClusterTorqueDispatcher<>(dispatcherConfig);


        }
        dispatcher.setLinearSystem(MastSystem.class);
        dispatcher.setRTTool(MastTool.class);
        dispatcher.setSeriesGenerator(UtilizationEvaluationEngine.class, generatorConfig);
        dispatcher.setResultsManager(resultsManager);


        //////////////////
        // Launch Study //
        //////////////////

        dispatcher.launch();

    }
}
