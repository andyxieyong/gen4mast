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

The properties file contains all the configuration information of Gen4MAST. It specifies among others the characteristics of the system to be generated, and which techniques to apply on them, from the techniques available in MAST (e.g. schedulability analysis, prioritity assignment, etc.).

The file follows the standard Java properties file [format](https://docs.oracle.com/javase/7/docs/api/java/util/Properties.html), in its plain text version (non XML). 

Each key in the properties file can be assigned several values separated by comma. For example, parameter **N_FLOWS**=4,6 will generate two kinds of systems: systems with 4 end-to-end flows, and systems with 6 end-to-end flows.

All properties must be specified in the file, no defaults values are assumed.

## System characteristics section

This properties specify the characteristics of the systems to be generated.

| Property | Description |
| ---------| ----------- |
| SEED     | Seed to initialize the random number generator. Different  seeds can be given to generate several systems with the same characteristics, but created with different seeds. Optionally, a range of seeds can be provided: SEED=1-5 is equivalent to SEED=1,2,3,4,5|
| N_PROCESSORS | Number of processing resources in the system |
| N_FLOWS | Number of end-to-end flows in the system | 
| N_TASKS | Maximum number of steps in the end-to-end flows | 
| RANDOM_LENGTH | Boolean. *False* indicates that every end-to-end flow has **N_TASKS** steps. *True* indicates that each end-to-end flow will have a random number of steps in the range [2,**N_STEPS**] | 
| SINGLE_FLOWS | Indicates the percentage of end-to-end flows that will have only one step (independent tasks) | 
| SCHED_POLICY | Scheduling Policy used: *FP* for fixed priorities scheduling, or *EDF* for earliest deadline first scheduling  | 
| PERIOD_DISTRIBUTION | Probability distribution used to select the end-to-end flows periods: *UNIFORM* uniform distribution, *LOG-UNIFORM* logarithmic-uniform distribution | 
| PERIOD_BASE | Minimum value that the periods can have | 
| PERIOD_RATIO |  Is used to indicate the maximum possible value of the periods, calculated as **PERIOD_BASE** * **PERIOD_RATIO**| 
| DEADLINE | Indicates which end-to-end deadlines to assign to the end-to-end flows. If an integer is provided, the deadlines are calculated as **DEADLINE* * *period. Additionaly, predefined values in the segment [T, N*T] are established (T and N are respectively the period and number of steps of the end-to-end flow). the predefined values are: *T,NT* for the limits of the range, *T1,T2* for the first and second thirds of the segment, and *Q1,Q2,Q3* for the first, second and third quarters of the segment | 
| TASK_LOCALIZATION | Established how steps should be statically assigned to a processing resource: *RANDOM* assigns steps to processing resources randomly, *AVOID_CONSECUTIVE* avoids if possible two consecutive steps to be located in the same processing resource, *AVOID_REPETITION* avoids if possible that the end-to-end flows repeat a processing resource. | 
| UTILIZATION | Average system utilization, given as percentage | 
| UTILIZATION_BCET_FACTOR | Normalized factor to establish best-case execution times of the steps, between 0 and 1. A value of 1 indicates best-case execution times equal to worst-case execution times| 
| UTILIZATION_WCET_METHOD | Method used to calculate the worst-case execution times of the steps:  *SCALE* assings the same utilization to every step, and *UUNIFAST* uses the UUnifast algorithm [link](http://retis.sssup.it/~giorgio/paps/2005/rtsj05-bini.pdf) | 
| UTILIZATION_BALANCING | Indicates the load balancing among the processing resources: *BALANCED* force every processing resource to have the same utilization, and with *NON_BALANCED* each processing resource have different utilizations| 
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

## Results Manager options

| Property | Description |
| ---------| ----------- |
| STORER | |
| RESULTS_LOCATION | |
----------------------
