package es.unican.istr.rtgen;

import es.unican.istr.rtgen.storers.config.StorerConfig;
import es.unican.istr.rtgen.system.LinearSystem;

/**
 * Created by juanm on 13/01/2016.
 */
public abstract class Storer {

    ///////////////////
    // Storer Fields //
    ///////////////////

    private StorerConfig config;


    //////////////////
    // Constructors //
    //////////////////

    public Storer(StorerConfig config){
        this.config = config;
    }


    /////////////////////////////
    // Results Storage Methods //
    /////////////////////////////

    public abstract void storeSystemResults(LinearSystem sys);


    /////////////
    // Getters //
    /////////////

    public StorerConfig getConfig() {
        return config;
    }
}
