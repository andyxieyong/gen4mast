package es.unican.istr.rtgen.tool;

import es.unican.istr.rtgen.exceptions.InterruptedAnalysis;
import es.unican.istr.rtgen.system.LinearSystem;
import es.unican.istr.rtgen.tool.config.ToolConfig;

/**
 * Created by juanm on 19/08/2015.
 */
public interface Tool {

    /*
    The implementation of this method applies a Real-Time tool on LinearSystem, and stores the results in it
    The execution is configured with an implementation of a ToolConfig
     */
    public void analyze(LinearSystem system, ToolConfig config) throws InterruptedAnalysis;

    public void clean();

}
