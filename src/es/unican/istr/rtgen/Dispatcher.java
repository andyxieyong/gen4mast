package es.unican.istr.rtgen;

import es.unican.istr.rtgen.dispatchers.config.DispatcherConfig;
import es.unican.istr.rtgen.generators.config.GeneratorConfig;
import es.unican.istr.rtgen.system.LinearSystem;
import es.unican.istr.rtgen.tool.Tool;

/**
 * Created by juanm on 13/01/2016.
 */
public abstract class Dispatcher<G extends EvaluationEngine, LS extends LinearSystem, RTT extends Tool> {

    /////////////////////////////////
    // Execution Dispatcher Fields //
    /////////////////////////////////

    private DispatcherConfig dispatcherConfig;


    /////////////////////////////
    // Series EvaluationEngine Fields //
    /////////////////////////////

    private Class<G> TGenerator;
    private GeneratorConfig seriesConfig;


    ////////////////////////////
    // Results Handler Fields //
    ////////////////////////////

    private ResultsManager resultsManager;


    ///////////////////////////////////
    // System and Tool Models Fields //
    ///////////////////////////////////

    private Class<LS> TLinearSystem;
    private Class<RTT> TRTTool;


    //////////////////
    // Constructors //
    //////////////////

    public Dispatcher(DispatcherConfig dispatcherConfig){
        this.dispatcherConfig = dispatcherConfig;
    }


    ////////////////////////
    // Dispatcher Methods //
    ////////////////////////

    public abstract void launch();

    public Boolean isConfigurationOK(){
        if (resultsManager == null || TLinearSystem == null || TRTTool == null || dispatcherConfig == null ||
                TGenerator == null || seriesConfig == null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    ///////////////////////////
    // Configuration Methods //
    ///////////////////////////

    public void setSeriesGenerator(Class<G> TSeriesGenerator, GeneratorConfig seriesConfig){
        this.TGenerator = TSeriesGenerator;
        this.seriesConfig = seriesConfig;
    }

    public void setResultsManager(ResultsManager resultsManager){
        this.resultsManager = resultsManager;
    }

    public void setLinearSystem(Class<LS> TLinearSystem){
        this.TLinearSystem = TLinearSystem;
    }

    public void setRTTool(Class<RTT> TRTTool){
        this.TRTTool = TRTTool;
    }


    ////////////////////
    // Fields Getters //
    ////////////////////

    public DispatcherConfig getDispatcherConfig() {
        return dispatcherConfig;
    }

    public Class<G> getTGenerator() {
        return TGenerator;
    }

    public GeneratorConfig getSeriesConfig() {
        return seriesConfig;
    }

    public ResultsManager getResultsManager() {
        return resultsManager;
    }

    public Class<LS> getTLinearSystem() {
        return TLinearSystem;
    }

    public Class<RTT> getTRTTool() {
        return TRTTool;
    }
}
