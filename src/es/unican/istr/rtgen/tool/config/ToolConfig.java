package es.unican.istr.rtgen.tool.config;

/**
 * Created by juanm on 19/08/2015.
 */
public abstract class ToolConfig {

    public ToolConfig(){
        super();
    }

    public abstract void initWihArgs(String[] pairs);

    public abstract String[] getConfigAsArgs();

}
