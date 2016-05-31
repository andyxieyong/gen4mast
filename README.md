# What it is

Gen4MAST is a tool to automatically generate MAST models, and apply the MAST tool on them. This is a generalization using Java of the GEN4MAST prototype built using Python (http://mast.unican.es/gen4mast/).
Gen4MAST is provided as open source. An executable pre-compiled jar file is also available in this [link](https://github.com/rivasjm/gen4mast/raw/master/releases/gen4mast.jar/).

# Quick Tutorial for execution in the Local PC

This is a quick tutorial of Gen4MAST. This tutorial uses the jar release of Gen4MAST that can be downloaded from [here](https://github.com/rivasjm/gen4mast/raw/master/releases/gen4mast.jar/). 

To execute Gen4MAST, a properties file must be created. This file specifies the characteristics of the systems to be generated, and which MAST techniques to apply on them, such as schedulability analyses or priority optimizations. The properties file used in this tutorial can be downloaded [here] (https://github.com/rivasjm/gen4mast/raw/master/releases/gen4mast.properties). 

### Setting up the execution environment

Gen4MAST requires Java version 1.8. Before executing Gen4MAST, the user must modify the properties file to indicate the location of the MAST Analysis tool (mast_analysis.exe in windows). This is specified with key **MAST_PATH** in the provided properties file. 

### Executing Gen4MAST in the local PC

The properties file of this tutorial generates a synthetic pool of distributed systems using two different WCET generation methods: SCALE and UUNIFAST. This pool can be used to study the differences on schedulability of these two techniques. For this tutorial, the Offset-Based Analysis is applied to each generated system, and the results are stored in an SQLite database. Five different seeds numbers are used to generated the systems. A thread pool of size 4 is used to execute this study (parameter **THREAD_POOL_SIZE** of the properties file). 

Gen4MAST can be launched as a standard executable java jar file as follows:

```
java -jar gen4mast.jar path_to_properties_file
```

If the path to the properties file is not provided, Gen4MAST will look for a file called "gen4mast.properties" in the current directory.

The generation and execution of this tutorial takes a few seconds, depending on the computer used.

During the execution of Gen4MAST, a directory called **results** will be created, in which the intermediate MAST results are stored. This results are then compiled into a SQLite database in the current directory (results.db).

### SQLite Results Format

After executing Gen4MAST, a SQLite database with the results is generated. This database can be accessed programatically with any programming language that supports SQLite, for example Python (sqlite3 library). 

Additionally, the results database can be accessed using special purpose programs such as [DB Browser for SQLite](http://sqlitebrowser.org/).

# Properties File

All properties must be specified in the file, no defualts values are assumed.

## System characteristics section

| Property | Description |
| ---------| ----------- |
| SEED     |  |
| N_PROCESSORS | |
| N_FLOWS |  | 
| N_TASKS |  | 
| RANDOM_LENGTH |  | 
| SINGLE_FLOWS |  | 
| SCHED_POLICY |  | 
| PERIOD_DISTRIBUTION |  | 
| PERIOD_BASE |  | 
| PERIOD_RATIO |  | 
| DEADLINE |  | 
| TASK_LOCALIZATION |  | 
| UTILIZATION |  | 
| UTILIZATION_BCET_FACTOR |  | 
| UTILIZATION_WCET_METHOD |  | 
| UTILIZATION_BALANCING |  | 
-------------------------------------

## MAST Tool configuration

| Property | Description |
| ---------| ----------- |
| NAME |  | 
| WORK_PATH |  | 
| MAST_PATH |  | 
| ANALYSIS_TOOL |  | 
| ASSIGNMENT_TOOL |  | 
| SYNC |  | 
| HOSPA_INIT |  | 
| HOSPA_KA |  | 
| HOSPA_KR |  | 
| HOSPA_ITERATIONS |  | 
| HOSPA_OVERITERATIONS |  | 
| ANALYSIS_STOP_FACTOR |  | 
| LC_EDF_GSD |  | 
| LC_EDF_DS_FACTOR |  | 
| CALCULATE_SLACK |  | 
| JITTER_AVOIDANCE |  | 
| ANALYSIS_TIMEOUT |  | 
-----------------------------

## Dispatcher options

| Property | Description |
| ---------| ----------- |
| DISPATCHER |  | 
| THREAD_POOL_SIZE |  |
| GENERATOR_JAR_PATH |  | 
| CLUSTER_USER_EMAIL |  |  
| NUMBER_OF_CLUSTER_JOBS |  | 
-----------------------------

## Utilization Series Generator options

| Property | Description |
| ---------| ----------- |
| GENERATOR |  | 
| CLEAN_AFTER |  | 
| UTILIZATION_START |  | 
| UTILIZATION_STEP |  | 
| UTILIZATION_TOP |  | 
----------------------
