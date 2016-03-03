package es.unican.istr.rtgen.generators.config;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public class UtilizationGeneratorConfig extends GeneratorConfig {

    public UtilizationGeneratorConfig(String fileName){
        super(fileName);
    }

    public UtilizationGeneratorConfig(){
        super();
    }


    ////////////////////////////////////////
    // Methods for the Utilization Series //
    ////////////////////////////////////////

    public Integer getUtilizationStart(){
        String value = (String)getProp().get("UTILIZATION_START");
        if (value == null){
            return 1;
        } else {
            return Integer.parseInt(value);
        }
    }

    public Integer getUtilizationTop(){
        String value = (String)getProp().get("UTILIZATION_TOP");
        if (value == null){
            return 100;
        } else {
            return Integer.parseInt(value);
        }
    }

    public Integer getUtilizationStep(){
        String value = (String)getProp().get("UTILIZATION_STEP");
        if (value == null){
            return 1;
        } else {
            return Integer.parseInt(value);
        }
    }

    public Boolean getCleanAfterwards(){
        String value = (String)getProp().get("CLEAN_AFTER");
        if (value == null){
            return true;
        } else {
            return Boolean.parseBoolean(value);
        }
    }


}
