package es.unican.istr.gen4mast.config;

import es.unican.istr.gen4mast.dispatchers.DispatcherOptions;
import es.unican.istr.gen4mast.generators.GeneratorOptions;
import es.unican.istr.gen4mast.storers.ResultsOptions;
import es.unican.istr.rtgen.config.Config;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 18/01/2016.
 */
public class Gen4MASTConfig extends Config{

    public Gen4MASTConfig(String fileName){
        super(fileName);
    }

    public DispatcherOptions getDispatcher(){
        DispatcherOptions option = DispatcherOptions.valueOf(getProp().getProperty("DISPATCHER"));
        return option;
    }

    public GeneratorOptions getGenerator(){
        GeneratorOptions option = GeneratorOptions.valueOf(getProp().getProperty("GENERATOR"));
        return option;
    }

    public ResultsOptions getStorer(){
        ResultsOptions option = ResultsOptions.valueOf(getProp().getProperty("STORER"));
        return  option;
    }




}
