package es.unican.istr.rtgen.evaluationengines.config;

import es.unican.istr.rtgen.config.Config;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public abstract class GeneratorConfig extends Config {

    public GeneratorConfig(String fileName){
        super(fileName);
    }

    public GeneratorConfig(){
        super();
    }

}
