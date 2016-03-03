package es.unican.istr.rtgen.config;

import java.io.*;
import java.util.*;

/**
 * Created by juanm on 16/01/2016.
 */
public class Config {

    public static final String SEPARATOR = ",";
    private Properties prop;
    private String configFileLocation;


    //////////////////
    // Constructors //
    //////////////////
    // TODO: cuando en el fichero no se especifica un parametro (p.ej. UTILIZATION, falla)

    public Config(String fileName){
        try {
            //System.out.printf("Loading configuration parameters in %s\n", fileName);

            // Stores config file contents in properties object
            InputStream input = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(input);
            input.close();

            // Post process parameters
            postProcess();

            // Store location of config file
            configFileLocation = new File(fileName).getCanonicalPath();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Config(){
        this(getDefaultFileName());
    }


    ////////////////////////////////////
    // Post-processing of information //
    ////////////////////////////////////

    private void postProcess(){

        // SEED=1-10    ->      SEED=1,2,3,4,5,6,7,8,9,10
        try {
            int low = Integer.parseInt(this.prop.getProperty("SEED").split("-")[0]);
            int high = Integer.parseInt(this.prop.getProperty("SEED").split("-")[1]);
            StringJoiner joiner = new StringJoiner(",");
            for (int i=low; i<=high; i++){
                joiner.add(Integer.toString(i));
            }
            this.prop.setProperty("SEED", joiner.toString());
        } catch (Exception e) {} // if this functionality is not used, just ignore


    }


    ///////////////////
    // Misc. Methods //
    ///////////////////

    public Properties getProp(){
        return prop;
    }

    public String getConfigFileLocation(){
        return configFileLocation;
    }

    public static String getDefaultFileName(){
        return "gen4mast.properties";
    }


    ////////////////////////
    // Main (for testing) //
    ////////////////////////

    public static void main(String[] args) {

        Config c = new Config();

        System.out.println(c.getProp().getProperty("SEED"));

    }

}
