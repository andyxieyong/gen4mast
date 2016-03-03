package es.unican.istr.gen4mast;

import es.unican.istr.gen4mast.config.Gen4MASTConfig;
import es.unican.istr.gen4mast.dispatchers.config.MastClusterDispatcherConfig;
import es.unican.istr.gen4mast.dispatchers.config.MastLocalDispatcherConfig;
import es.unican.istr.gen4mast.storers.SQLiteMastUtilizationGeneratorStorer;
import es.unican.istr.gen4mast.system.MastSystem;
import es.unican.istr.gen4mast.tool.MastTool;
import es.unican.istr.rtgen.Dispatcher;
import es.unican.istr.rtgen.Generator;
import es.unican.istr.rtgen.Storer;
import es.unican.istr.rtgen.dispatchers.ClusterTorqueDispatcher;
import es.unican.istr.rtgen.dispatchers.LocalDispatcher;
import es.unican.istr.rtgen.dispatchers.config.DispatcherConfig;
import es.unican.istr.rtgen.generators.UtilizationGenerator;
import es.unican.istr.rtgen.generators.config.GeneratorConfig;
import es.unican.istr.rtgen.generators.config.UtilizationGeneratorConfig;
import es.unican.istr.rtgen.storers.config.StorerConfig;
import es.unican.istr.rtgen.system.LinearSystem;
import es.unican.istr.rtgen.tool.Tool;

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
        // Storer Initialization //
        ///////////////////////////

        StorerConfig storerConfig = null;
        Storer storer = null;
        switch (configMain.getStorer()){
            case SQLITE_MAST:
                storerConfig = new StorerConfig(configFilePath);
                storer = new SQLiteMastUtilizationGeneratorStorer(storerConfig);
                break;
        }


        //////////////////////////////
        // Generator Initialization //
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
        dispatcher.setSeriesGenerator(UtilizationGenerator.class, generatorConfig);
        dispatcher.setStorer(storer);


        //////////////////
        // Launch Study //
        //////////////////

        dispatcher.launch();

    }
}
