package es.unican.istr.rtgen.dispatchers.config;

/**
 * Created by Administrador on 19/01/2016.
 */
public abstract class ClusterTorqueDispatcherConfig extends DispatcherConfig {

    public ClusterTorqueDispatcherConfig(String fileName){
        super(fileName);
    }

    ///////////////////////////////////////////////
    // Methods for the Cluster-TORQUE Dispatcher //
    ///////////////////////////////////////////////

    public Integer getNumberOfClusterJobs(){
        String size = (String)getProp().get("NUMBER_OF_CLUSTER_JOBS");
        if (size == null){
            return 1;
        } else {
            return Integer.parseInt(size);
        }
    }

    public String getClusterComplex(){
        String complex = (String)getProp().get("CLUSTER_COMPLEX");
        if (complex == null){
            return "long";
        } else {
            return complex;
        }
    }

    public String getClusterUserEmail(){
        String name = (String)getProp().get("CLUSTER_USER_EMAIL");
        if (name == null){
            return "default";
        } else {
            return name;
        }
    }

    public String getClusterJobId(){
        String name = (String)getProp().get("CLUSTER_JOBS_ID");
        if (name == null){
            return "job";
        } else {
            return name;
        }
    }

    public String getGeneratorJarPath(){
        String name = (String)getProp().get("GENERATOR_JAR_PATH");
        if (name == null){
            return "MastUtilizationSeries.jar";
        } else {
            return name;
        }
    }


}
