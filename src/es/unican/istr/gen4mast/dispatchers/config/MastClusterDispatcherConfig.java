package es.unican.istr.gen4mast.dispatchers.config;

import com.google.common.collect.Sets;
import es.unican.istr.gen4mast.tool.config.MastToolConfig;
import es.unican.istr.gen4mast.tool.config.MastToolConfigurableMap;
import es.unican.istr.gen4mast.tool.config.MastToolConfigurableOptions;
import es.unican.istr.rtgen.dispatchers.config.ClusterTorqueDispatcherConfig;
import es.unican.istr.rtgen.dispatchers.config.LocalDispatcherConfig;
import es.unican.istr.rtgen.tool.config.ToolConfig;

import java.util.*;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */

public class MastClusterDispatcherConfig extends ClusterTorqueDispatcherConfig {

    //TODO hace falta poner el constructor? o ya usa el del super?

    public MastClusterDispatcherConfig(String fileName){
        super(fileName);
    }

    /////////////////////////
    // Study Scope Methods //
    /////////////////////////

    @Override
    public List<ToolConfig> getToolConfigs() {
        // Set of enums, ordered as in the enum definition
        EnumSet<MastToolConfigurableOptions> mastOptionsSet = EnumSet.allOf(MastToolConfigurableOptions.class);

        // List of given parameters
        List<Set<String>> mastGivenParams = new ArrayList<>();

        // Read parameters from configuration file
        for (MastToolConfigurableOptions s: mastOptionsSet){
            Object o = getProp().get(s.name().toUpperCase());
            if (o!=null) {
                String params = (String) o;
                mastGivenParams.add(new HashSet<>(Arrays.asList(params.split(SEPARATOR))));
            }
        }

        // Generate all parameter combinations
        Set<List<String>> mastCombinations = Sets.cartesianProduct(mastGivenParams);

        // System and MAST configuration maps
        MastToolConfigurableMap mastMap = new MastToolConfigurableMap();
        List<ToolConfig> configs = new ArrayList<>();

        for (List<String> mastCombination: mastCombinations) {

            // Create map with MAST options for this iteration (combination)
            Iterator<MastToolConfigurableOptions> i3 = mastOptionsSet.iterator();
            Iterator<String> i4 = mastCombination.iterator();
            while (i3.hasNext() && i4.hasNext()){
                mastMap.put(i3.next(), i4.next());
            }
            configs.add(new MastToolConfig(mastMap));
        }

        return configs;
    }

}
