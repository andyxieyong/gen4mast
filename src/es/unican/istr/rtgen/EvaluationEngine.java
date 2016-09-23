package es.unican.istr.rtgen;

import es.unican.istr.rtgen.generators.config.GeneratorConfig;
import es.unican.istr.rtgen.system.LinearSystem;
import es.unican.istr.rtgen.system.config.SystemConfig;
import es.unican.istr.rtgen.tool.Tool;
import es.unican.istr.rtgen.tool.config.ToolConfig;

/**
 * Created by juanm on 13/01/2016.
 */
public abstract class EvaluationEngine<LS extends LinearSystem, RTT extends Tool> implements Runnable  {

    /////////////////////////////
    // Series EvaluationEngine Fields //
    /////////////////////////////

    private GeneratorConfig genConfig;
    private SystemConfig sysConfig;
    private ToolConfig toolConfig;


    ////////////////////////////
    // Results Handler Fields //
    ////////////////////////////

    private Storer storer;


    ///////////////////////////////////
    // System and Tool Models Fields //
    ///////////////////////////////////

    private Class<LS> TLinearSystem;
    private Class<RTT> TRTTool;


    //////////////////
    // Constructors //
    //////////////////

    public EvaluationEngine(SystemConfig sysConfig, ToolConfig toolConfig,
                            Storer storer, GeneratorConfig genConfig){
        this.sysConfig = sysConfig;
        this.toolConfig = toolConfig;
        this.storer = storer;
        this.genConfig = genConfig;
    }


    //////////////////////////////
    // Series EvaluationEngine Methods //
    //////////////////////////////

    //public abstract void run(); // Defined in Interface Runnable


    ///////////////////////////
    // Configuration Methods //
    ///////////////////////////

    public void setLinearSystem(Class<LS> TLinearSystem){
        this.TLinearSystem = TLinearSystem;
    }

    public void setRTTool(Class<RTT> TRTTool){
        this.TRTTool = TRTTool;
    }


    ////////////////////
    // Fields Getters //
    ////////////////////

    public GeneratorConfig getGenConfig() {
        return genConfig;
    }

    public SystemConfig getSysConfig() {
        return sysConfig;
    }

    public ToolConfig getToolConfig() {
        return toolConfig;
    }

    public Storer getStorer() {
        return storer;
    }

    public Class<LS> getTLinearSystem() {
        return TLinearSystem;
    }

    public Class<RTT> getTRTTool() {
        return TRTTool;
    }
}
