package es.unican.istr.tests;

import es.unican.istr.gen4mast.dispatchers.config.MastClusterDispatcherConfig;
import es.unican.istr.gen4mast.storers.SQLiteMastUtilizationGeneratorResultsManager;
import es.unican.istr.gen4mast.system.MastSystem;
import es.unican.istr.gen4mast.tool.MastTool;
import es.unican.istr.rtgen.Dispatcher;
import es.unican.istr.rtgen.ResultsManager;
import es.unican.istr.rtgen.dispatchers.ClusterTorqueDispatcher;
import es.unican.istr.rtgen.dispatchers.config.DispatcherConfig;
import es.unican.istr.rtgen.evaluationengines.UtilizationEvaluationEngine;
import es.unican.istr.rtgen.evaluationengines.config.GeneratorConfig;
import es.unican.istr.rtgen.evaluationengines.config.UtilizationGeneratorConfig;
import es.unican.istr.rtgen.resultsmanagers.config.StorerConfig;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 21/01/2016.
 */
public class ClusterDispatcherTest {

    public static void main(String[] args) {

        DispatcherConfig dispatcherConfig = new MastClusterDispatcherConfig("gen4mast.properties");

        // Create execution dispatcher (Local)
        Dispatcher<UtilizationEvaluationEngine, MastSystem, MastTool> dispatcher = new ClusterTorqueDispatcher<>(dispatcherConfig);

        // Set Series EvaluationEngine (UtilizationSeries)
        GeneratorConfig seriesConfig = new UtilizationGeneratorConfig();
        dispatcher.setSeriesGenerator(UtilizationEvaluationEngine.class, seriesConfig);

        // Set Results Handler
        StorerConfig resultsConfig = new StorerConfig();
        ResultsManager results = new SQLiteMastUtilizationGeneratorResultsManager(resultsConfig);
        dispatcher.setResultsManager(results);

        // Set system and tool models
        dispatcher.setLinearSystem(MastSystem.class);
        dispatcher.setRTTool(MastTool.class);

        // Launch Dispatcher
        dispatcher.launch();

    }

}
