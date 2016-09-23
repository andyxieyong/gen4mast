package es.unican.istr.rtgen.dispatchers;

import es.unican.istr.rtgen.Dispatcher;
import es.unican.istr.rtgen.EvaluationEngine;
import es.unican.istr.rtgen.dispatchers.config.ClusterTorqueDispatcherConfig;
import es.unican.istr.rtgen.dispatchers.config.DispatcherConfig;
import es.unican.istr.rtgen.system.LinearSystem;
import es.unican.istr.rtgen.system.config.SystemConfig;
import es.unican.istr.rtgen.tool.Tool;
import es.unican.istr.rtgen.tool.config.ToolConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrador on 19/01/2016.
 */
public class ClusterTorqueDispatcher<G extends EvaluationEngine, LS extends LinearSystem, RTT extends Tool>
        extends Dispatcher<G, LS, RTT> {

    //////////////////
    // Constructors //
    //////////////////

    public ClusterTorqueDispatcher(DispatcherConfig dispatcherConfig) {
        super(dispatcherConfig);
    }


    ////////////////////////
    // Dispatcher Methods //
    ////////////////////////

    @Override
    public void launch() {

        System.out.printf("Cluster-TORQUE dispatcher launched\n");
        if (!isConfigurationOK()){
            throw new RuntimeException("ERROR: Dispatcher is not fully configured");
        }

        /////////////////////////
        // Prepare Directories //
        ////////////////////////

        Path outPath = null;
        Path outErrorPath = null;
        Path scriptsPath  = null;
        try {
            outPath = Paths.get(System.getProperty("user.dir"), "cluster_files", "out");
            Files.createDirectories(outPath);
            outErrorPath = Paths.get(System.getProperty("user.dir"), "cluster_files", "out_error");
            Files.createDirectories(outErrorPath);
            scriptsPath = Paths.get(System.getProperty("user.dir"), "cluster_files", "scripts");
            Files.createDirectories(scriptsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Cast dispatcher config (expects ClusterTorqueDispatcherConfig
        ClusterTorqueDispatcherConfig config = (ClusterTorqueDispatcherConfig) getDispatcherConfig();

        // Reads dispatcher configuration
        String email = config.getClusterUserEmail();
        Integer nJobs = config.getNumberOfClusterJobs();
        String complex = config.getClusterComplex();

        // Loads study scope
        List<SystemConfig> systemConfigs = config.getSystemConfigs();
        List<ToolConfig> toolConfigs = config.getToolConfigs();
        Integer totalNumberExecutions = systemConfigs.size()*toolConfigs.size();

        // Iterate study scope
        try {
            System.out.printf("Study composed of %d jobs: \n", totalNumberExecutions);
            System.out.printf("Number of cluster jobs to be sent: %d\n", Math.min(nJobs, totalNumberExecutions));
            System.out.printf("Cluster user email: %s\n", email);
            System.out.printf("Cluster execution comples: %s\n", complex);
            System.out.println("Press any key to launch the study...");
            System.in.read();

            Integer slotsInEachJob = (int)Math.ceil(totalNumberExecutions.doubleValue()/nJobs.doubleValue());
            System.out.printf("Slota in each cluster job: %d\n", slotsInEachJob);

            ///////////////////
            // Iterate Scope //
            ///////////////////

            Integer currentJob = 1;
            Integer slotsLeft = slotsInEachJob;
            List<String> cmdsInJob = new ArrayList<>();


            Iterator<SystemConfig> is = systemConfigs.iterator();
            Iterator<ToolConfig> it;
            while (is.hasNext()){
                SystemConfig s = is.next();

                it = toolConfigs.iterator();
                while (it.hasNext()){

                    ToolConfig t = it.next();

                    /////////////////////////////////
                    // Prepare EvaluationEngine Execution //
                    /////////////////////////////////

                    List<String> argsL = new ArrayList<>();

                    // ID
                    argsL.add(currentJob.toString());

                    // Config file location
                    argsL.add(getDispatcherConfig().getConfigFileLocation());

                    // LinearSystem implementation class
                    argsL.add(getTLinearSystem().getName());

                    // Tool implementation class
                    argsL.add(getTRTTool().getName());

                    // EvaluationEngine config class
                    argsL.add(getSeriesConfig().getClass().getName());

                    // Storer config class
                    argsL.add(getStorer().getConfig().getClass().getName());

                    // Storer class
                    argsL.add(getStorer().getClass().getName());

                    // Systemconfig class
                    argsL.add(s.getClass().getName());

                    // SystemConfig as args
                    argsL.add(String.valueOf(s.getConfigAsArgs().length));
                    argsL.addAll(Arrays.asList(s.getConfigAsArgs()));

                    // ToolConfig class
                    argsL.add(t.getClass().getName());

                    // ToolConfig as args
                    argsL.add(String.valueOf(t.getConfigAsArgs().length));
                    argsL.addAll(Arrays.asList(t.getConfigAsArgs()));

                    // Create command
                    String cmd = String.format("  java -Xms256m -Xmx256m -XX:-UseCompressedClassPointers -jar %s %s", config.getGeneratorJarPath(), String.join(" ", argsL));
                    // If -XX:-UseCompressedClassPointers is not provided, Could not allocate metaspace: 1073741824 bytes error is raised in the cluster
                    cmdsInJob.add(cmd);

                    slotsLeft -= 1;

                    if (slotsLeft == 0 || (!is.hasNext() && !it.hasNext())){  // Create and launch script when slots are emptied, or in last iteration
                        // Write script
                        createClusterScript(
                                Paths.get(scriptsPath.toAbsolutePath().toString(), String.format("launch%d.sh", currentJob)).toAbsolutePath().toString(),
                                System.getProperty("user.dir"),
                                String.format("%s%d", config.getClusterJobId(), currentJob),
                                email,
                                outPath.toAbsolutePath().toString(),
                                outErrorPath.toAbsolutePath().toString(),
                                complex,
                                String.join("\n", cmdsInJob));

                        // Execute script
                        Runtime r = Runtime.getRuntime();
                        Process p;
                        try {
                            System.out.printf("Launching %s\n", Paths.get(scriptsPath.toAbsolutePath().toString(), String.format("launch%d.sh", currentJob)).toAbsolutePath().toString());
                            p = r.exec(Paths.get(scriptsPath.toAbsolutePath().toString(), String.format("launch%d.sh", currentJob)).toAbsolutePath().toString());
                            p.waitFor();
                            Thread.sleep(500);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        // Start new cluster job
                        cmdsInJob.clear();
                        slotsLeft = slotsInEachJob;
                        currentJob += 1;


                    }
                }
            }


        } catch (IOException e){
            e.printStackTrace();
        }
    }


    ///////////////////////////////////////
    // Generate cluster execution script //
    ///////////////////////////////////////

    private void createClusterScript(String fileName, String workPath, String jobName, String email,
                                     String outputPath, String errorPath, String complex, String cmd){
        String template = "#!/bin/sh\n" +
                "user_name=$USER\n" +
                "work_path=\"%s/\"\n" +              // Work path
                "shell_name=\"/bin/bash\"\n" +
                "job_name=\"%s\"\n" +               // Job name
                "email=%s\n" +                      // Email
                "output_path=\"%s/\"\n" +                // Output path
                "error_path=\"%s/\"\n" +                 // Error path
                "complex=\"%s\"\n" +                // Complex
                "# job sent\n" +
                "qsub << eor\n" +
                "#!/bin/bash\n" +
                "#$ -S $shell_name\n" +
                "#$ -N $job_name\n" +
                "#$ -o /dev/null\n" +
                "#$ -e /dev/null\n" +
                "#$ -v output_path=$output_path\n" +
                "#$ -v error_path=$error_path\n" +
                "#$ -V\n" +
                "#$ -M $email\n" +
                "#$ -m n\n" +
                "#$ -l $complex\n" +
                "kinit -k -t /home/Keytab/krb5.keytab.$USER $USER\n" +
                "aklog -c ATC.UNICAN.ES -k ATC.UNICAN.ES\n" +
                "export LD_LIBRARY_PATH=\"/afs/atc.unican.es/u/r/rivasjm/mini-dist/usr/lib/:/afs/atc.unican.es/u/r/rivasjm/mini-dist/lib/python/tables/:/afs/atc.unican.es/u/r/rivasjm/mini-dist/lib\"\n" +
                "#\n" +
                "if [ true ]; then\n" +
                "  cd %s\n" +
                "%s\n" +                            // Command
                "fi 1>\\$output_path\\$REQUEST.o\\$JOB_ID 2>\\$error_path\\$REQUEST.e\\$JOB_ID\n" +
                "eof";

        try {
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.format(template, workPath, jobName, email,
                     outputPath, errorPath, complex, workPath, cmd));
            bw.close();
            file.setExecutable(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
