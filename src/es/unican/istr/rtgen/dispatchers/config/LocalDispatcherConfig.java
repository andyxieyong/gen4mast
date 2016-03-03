package es.unican.istr.rtgen.dispatchers.config;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public abstract class LocalDispatcherConfig extends DispatcherConfig {


    public LocalDispatcherConfig(String fileName){
        super(fileName);
    }

    ////////////////////////////////////////////////
    // Methods for the Local Execution Dispatcher //
    ////////////////////////////////////////////////

    public Integer getThreadPoolSize(){
        String size = (String)getProp().get("THREAD_POOL_SIZE");
        if (size == null){
            return 1;
        } else {
            return Integer.parseInt(size);
        }
    }

}
