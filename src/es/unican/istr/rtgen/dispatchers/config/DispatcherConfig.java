package es.unican.istr.rtgen.dispatchers.config;

import com.google.common.collect.Sets;
import es.unican.istr.rtgen.config.Config;
import es.unican.istr.rtgen.system.config.LinearSystemConfigurableMap;
import es.unican.istr.rtgen.system.config.LinearSystemConfigurableOptions;
import es.unican.istr.rtgen.system.config.SystemConfig;
import es.unican.istr.rtgen.tool.config.ToolConfig;

import java.util.*;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 17/01/2016.
 */
public abstract class DispatcherConfig extends Config {

    public DispatcherConfig(String fileName){
        super(fileName);
    }

    public abstract List<ToolConfig> getToolConfigs();

    public List<SystemConfig> getSystemConfigs() {
        // Set of enums, ordered as in the enum definition
        EnumSet<LinearSystemConfigurableOptions> systemOptionsSet = EnumSet.allOf(LinearSystemConfigurableOptions.class);

        // List of given parameters
        List<Set<String>> systemGivenParams = new ArrayList<>();

        // Load parameters from file, store as given parameters
        for (LinearSystemConfigurableOptions s: systemOptionsSet){
            Object o = getProp().get(s.name());
            if (o!=null) {
                String params = (String) o;
                systemGivenParams.add(new HashSet<>(Arrays.asList(params.split(SEPARATOR))));
            }
        }

        // Generate all parameter combinations
        Set<List<String>> systemCombinations = Sets.cartesianProduct(systemGivenParams);

        // System configuration map
        LinearSystemConfigurableMap systemMap = new LinearSystemConfigurableMap();
        List<SystemConfig> configs = new ArrayList<>();

        for (List<String> systemCombination: systemCombinations) {

            // Create map with system characteristics for this iteration (combination)
            Iterator<LinearSystemConfigurableOptions> i1 = systemOptionsSet.iterator();
            Iterator<String> i2 = systemCombination.iterator();
            while (i1.hasNext() && i2.hasNext()) {
                systemMap.put(i1.next(), i2.next());
            }

            // Transform map to SystemConfig, and save to returned list
            //System.out.println(systemMap);
            configs.add(new SystemConfig(systemMap));
        }
        return configs;
    }

}
