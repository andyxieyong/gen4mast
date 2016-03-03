package es.unican.istr.tests;

import es.unican.istr.gen4mast.dispatchers.config.MastLocalDispatcherConfig;
import es.unican.istr.gen4mast.storers.SQLiteMastUtilizationGeneratorStorer;
import es.unican.istr.gen4mast.system.MastSystem;
import es.unican.istr.gen4mast.tool.MastTool;
import es.unican.istr.rtgen.Dispatcher;
import es.unican.istr.rtgen.dispatchers.config.DispatcherConfig;
import es.unican.istr.rtgen.Storer;
import es.unican.istr.rtgen.storers.config.StorerConfig;
import es.unican.istr.rtgen.generators.config.GeneratorConfig;
import es.unican.istr.rtgen.generators.config.UtilizationGeneratorConfig;
import es.unican.istr.rtgen.dispatchers.LocalDispatcher;
import es.unican.istr.rtgen.generators.UtilizationGenerator;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public class LocalDispatcherTest {

    public static void main(String[] args) {

        DispatcherConfig dispatcherConfig = new MastLocalDispatcherConfig("gen4mast.properties");

        // Create execution dispatcher (Local)
        Dispatcher<UtilizationGenerator, MastSystem, MastTool> dispatcher = new LocalDispatcher<>(dispatcherConfig);

        // Set Series Generator (UtilizationSeries)
        GeneratorConfig seriesConfig = new UtilizationGeneratorConfig();
        dispatcher.setSeriesGenerator(UtilizationGenerator.class, seriesConfig);

        // Set Results Handler
        StorerConfig resultsConfig = new StorerConfig();
        Storer results = new SQLiteMastUtilizationGeneratorStorer(resultsConfig);
        dispatcher.setStorer(results);

        // Set system and tool models
        dispatcher.setLinearSystem(MastSystem.class);
        dispatcher.setRTTool(MastTool.class);

        // Launch Dispatcher
        dispatcher.launch();

    }

}
