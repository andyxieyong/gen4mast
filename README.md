# Gen4MAST

Gen4MAST is a tool to automatically generate MAST models, and apply the MAST tool on them. This is a generalization using Java of the GEN4MAST prototype built using Python (http://mast.unican.es/gen4mast/).
Gen4MAST is provided as open source. An executable pre-compiled jar file is also available in this [link](https://github.com/rivasjm/gen4mast/raw/master/releases/gen4mast.jar/).

# Quick Tutorial

This is a quick tutorial of Gen4MAST. This tutorial uses the jar release of Gen4MAST that can be downloaded from [here](https://github.com/rivasjm/gen4mast/raw/master/releases/gen4mast.jar/). 

To execute Gen4MAST, a properties file must be created. This file specifies the characteristics of the systems to be generated, and which MAST techniques to apply on them, such as schedulability analyses or priority optimizations. The properties file used in this tutorial can be downloaded [here] (https://github.com/rivasjm/gen4mast/raw/master/releases/gen4mast.properties). 

## Setting up the execution environment

Gen4MAST requires Java version 1.8.

## Execuring Gen4MAST

Gen4MAST can be launched as a standard executable java jar file as follows:

```
java -jar gen4mast.jar path_to_properties_file
```

If the path to the properties file is not proviedd, Gen4MAST will look for a file called "gen4mast.properties" in the current directory.
