package es.unican.istr.rtgen.resultsmanagers.config;

import es.unican.istr.rtgen.config.Config;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public class StorerConfig extends Config {


    public StorerConfig(String fileName){
        super(fileName);
    }

    public StorerConfig(){
        super();
    }

    /////////////////////////////////////
    // Methods for the Results Handler //
    /////////////////////////////////////

    public String getResultsFileLocation(){
        String loc = (String)getProp().get("RESULTS_LOCATION");
        if (loc == null){
            return "results.db";
        } else {
            return loc;
        }
    }

    public void setResultsFileLocation(String newLocation){
        getProp().setProperty("RESULTS_LOCATION", newLocation);
    }

}
