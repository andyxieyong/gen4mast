package es.unican.istr.rtgen.dispatchers;

import es.unican.istr.rtgen.Dispatcher;
import es.unican.istr.rtgen.dispatchers.config.DispatcherConfig;
import es.unican.istr.rtgen.dispatchers.config.LocalDispatcherConfig;
import es.unican.istr.rtgen.generators.config.GeneratorConfig;
import es.unican.istr.rtgen.Generator;
import es.unican.istr.rtgen.Storer;
import es.unican.istr.rtgen.system.LinearSystem;
import es.unican.istr.rtgen.system.config.SystemConfig;
import es.unican.istr.rtgen.tool.Tool;
import es.unican.istr.rtgen.tool.config.ToolConfig;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by juanm on 17/01/2016.
 */
public class LocalDispatcher<G extends Generator, LS extends LinearSystem, RTT extends Tool>
        extends Dispatcher<G, LS, RTT> {

    //////////////////
    // Constructors //
    //////////////////

    public LocalDispatcher(DispatcherConfig dispatcherConfig) {
        super(dispatcherConfig);
    }


    ////////////////////////
    // Dispatcher Methods //
    ////////////////////////

    @Override
    public void launch() {

        System.out.printf("Local job dispatcher launched\n");
        if (!isConfigurationOK()){
            throw new RuntimeException("ERROR: Dispatcher is not fully configured");
        }

        // Cast dispatcher config to LocalDispatcherConfigHandler
        LocalDispatcherConfig localDispatcherConfig = (LocalDispatcherConfig) getDispatcherConfig();

        // Reads thread pool size
        Integer poolSize = localDispatcherConfig.getThreadPoolSize();
        System.out.printf("Thread pool of size %d\n", poolSize);

        // Create thread pool
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);

        // Loads study scope
        List<SystemConfig> systemConfigs = localDispatcherConfig.getSystemConfigs();
        List<ToolConfig> toolConfigs = localDispatcherConfig.getToolConfigs();

        try {

            System.out.printf("Study composed of %d jobs: \n", systemConfigs.size()*toolConfigs.size());
            System.out.println("Press any key to launch the study");
            System.in.read();
            // For each combination in the scope, launch a Series Generator
            for (SystemConfig s : systemConfigs) {
                for (ToolConfig r : toolConfigs) {

                    // Create and configure a Series Generator
                    Constructor<G> seriesConstructor = getTGenerator().getDeclaredConstructor(
                            SystemConfig.class, ToolConfig.class,
                            Storer.class, GeneratorConfig.class);

                    G series = seriesConstructor.newInstance(s, r, getStorer(), getSeriesConfig());

                    series.setLinearSystem(getTLinearSystem());
                    series.setRTTool(getTRTTool());

                    // Add execution of the series to the thread pool, and execute
                    pool.execute(series);
                }
            }
            pool.shutdown();


        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        } catch (NoSuchMethodException e4){
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }

    }
}
