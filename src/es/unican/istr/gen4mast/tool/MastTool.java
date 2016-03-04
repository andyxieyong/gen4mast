package es.unican.istr.gen4mast.tool;

import es.unican.istr.gen4mast.tool.config.HOSPAConfig;
import es.unican.istr.rtgen.exceptions.InterruptedAnalysis;
import es.unican.istr.gen4mast.system.MastSystem;
import es.unican.istr.gen4mast.tool.config.AnalysisOptions;
import es.unican.istr.gen4mast.tool.config.AssignmentOptions;
import es.unican.istr.gen4mast.tool.config.MastToolConfig;
import es.unican.istr.rtgen.system.LinearSystem;
import es.unican.istr.rtgen.tool.Tool;
import es.unican.istr.rtgen.tool.config.ToolConfig;
import es.unican.istr.gen4mast.tool.xmlmodels.results.REALTIMESITUATION;
import es.unican.istr.gen4mast.tool.xmlmodels.results.TimingResult;
import es.unican.istr.gen4mast.tool.xmlmodels.results.TransactionResults;
import es.unican.istr.rtgen.utils.Utils;
import org.apache.commons.exec.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Juan M Rivas (jmrivasconcepcion@gmail.com) on 19/08/2015.
 */

public class MastTool implements Tool {

    private String id;
    private Path workingDirectory = null;

    public MastTool(String id){
        this.id = id;
    }

    @Override
    public void analyze(LinearSystem system, ToolConfig config) throws InterruptedAnalysis{

        MastToolConfig c = (MastToolConfig) config;
        MastSystem s = (MastSystem) system;

        // Checks if mast_analysis.exe exists, and is executable
        if (!new File(c.getMastPath()).canExecute()){
            throw new RuntimeException(String.format("ERROR: mast_analysis.exe could not be found:\n %s\n", c.getMastPath()));
        }

        // Prepare working directory
        //String baseDir = FilenameUtils.concat(c.getWorkPath(), id);
        workingDirectory = Paths.get(FilenameUtils.concat(c.getWorkPath(), id));
        new File(workingDirectory.toAbsolutePath().toString()).mkdirs();

//        String inputFilePath = FilenameUtils.concat(baseDir, String.format("system%d.txt",new Double(system.getSystemUtilization()*100).intValue()));
//        String outputNewModelPath = FilenameUtils.concat(baseDir, String.format("system_new_%d.xml",new Double(system.getSystemUtilization()*100).intValue()));
//        String outputFilePath = FilenameUtils.concat(baseDir, String.format("results%d.xml", new Double(system.getSystemUtilization()*100).intValue()));
//        String stdOutputFilePath = FilenameUtils.concat(baseDir, String.format("mast_out%d.txt", new Double(system.getSystemUtilization()*100).intValue()));

        Path inputFilePath = Paths.get(workingDirectory.toAbsolutePath().toString(), String.format("system%d.txt",new Double(system.getSystemUtilization()*100).intValue()));
        Path outputNewModelPath = Paths.get(workingDirectory.toAbsolutePath().toString(), String.format("system_new_%d.xml",new Double(system.getSystemUtilization()*100).intValue()));
        Path outputFilePath = Paths.get(workingDirectory.toAbsolutePath().toString(), String.format("results%d.xml", new Double(system.getSystemUtilization()*100).intValue()));
        Path stdOutputFilePath = Paths.get(workingDirectory.toAbsolutePath().toString(), String.format("mast_out%d.txt", new Double(system.getSystemUtilization()*100).intValue()));
        Path hospaParamsPath = Paths.get(workingDirectory.toAbsolutePath().toString(), String.format("hospa_params_%d.txt",new Double(system.getSystemUtilization()*100).intValue()));

        //Write system to file
        system.writeSystem(new File(inputFilePath.toAbsolutePath().toString()));

        //Prepares MAST arguments string
        ArrayList<String> args = new ArrayList<>();

        //MAST Path
        args.add(c.getMastPath());

        //Analysis Tool
        args.add(AnalysisOptions.mapArg(c.getAnalysis()));

        //Clock Synchronization
        if (!c.getSync()) args.add("-l");

        //Analysis stop factor
        if (c.getStopFactor()>0.0) args.add(String.format(Locale.US, "-stop_factor %d", c.getStopFactor().intValue()));

        //Force global scheduling deadlines (LC-EDF-GSD)
        if (c.getGsd()) args.add("-force_global");

        //LC-EDF-DS factor
        if (c.getDsFactor()>0 && c.getDsFactor()!=1) args.add(String.format(Locale.US, "-scale_vd %d", c.getDsFactor()));

        //Slack
        if (c.getCalculateSlack()) args.add("-s");

        //Jitter Avoidance
        if (c.getJitterAvoidance()) args.add("-jitter_avoidance");

        //Scheduling Parameter assignment tool
        args.add(AssignmentOptions.mapArg(c.getAssignment()));

        //If HOSPA is used, create HOSPA params file
        createHospaParamsFile(new File(hospaParamsPath.toAbsolutePath().toString()), c.getHospaConfig());
        args.add(String.format("-a \"%s\"", hospaParamsPath.toAbsolutePath().toString()));

        //If new assignment is performed, save updated model with new assignment
        if (c.getAssignment() != AssignmentOptions.NONE){
            args.add(String.format("-d \"%s\"", outputNewModelPath.toAbsolutePath().toString()));
        }

        //Input and output files
        args.add(String.format("\"%s\"", inputFilePath.toAbsolutePath().toString()));
        args.add(String.format("\"%s\"", outputFilePath.toAbsolutePath().toString()));

        //Create string
        String cmd = String.join(" ", args);
        //System.out.println(cmd);

        //Store tool configuration in system
        system.setToolConfig(config);

        //Execute MAST Tool
        long beforeTime = System.nanoTime();
        //Runtime r = Runtime.getRuntime();
        //Process p = null;

        //Check if input file is not yet created
//        while (true) {
//            System.out.println(inputFilePath);
//            System.out.println("File length: "+new File(inputFilePath).length());
//            break;
//        }

        try {
            //p = r.exec(cmd);
            //p.waitFor();

            long timeout;
            if (c.getAnalysisTimeout() <= 0){
                timeout = ExecuteWatchdog.INFINITE_TIMEOUT;
            } else {
                timeout = c.getAnalysisTimeout();
            }

            //System.out.println(cmd);
            launchCommand(cmd, timeout, stdOutputFilePath.toAbsolutePath().toString(), workingDirectory.toAbsolutePath().toString());

            //System.out.println("despues");

//          String text = IOUtils.toString(p.getInputStream(), StandardCharsets.UTF_8.name());
//          System.out.println(text);

            long afterTime = System.nanoTime();
            integrateMASTResults(new File(outputFilePath.toAbsolutePath().toString()), system);

            // TODO: integrar prioridades nuevas desde outputNewModelPath

            system.setToolTimeElapsed(Utils.ns2ms(afterTime-beforeTime)); // Time stored in ms

        } catch (InterruptedAnalysis e){
            System.out.printf(" Analysis Timed Out.");
            // e.printStackTrace();
            throw e;
        }
//        finally {
//            if (cleanAfter){
//                try {
//                    Files.deleteIfExists(inputFilePath);
//                    Files.deleteIfExists(outputNewModelPath);
//                    Files.deleteIfExists()
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            //FileUtils.deleteQuietly(new File("mast_parser.lis"));
//        }

//        // Delete generated file
//        if (cleanAfter){
//            try {
//                FileUtils.deleteDirectory(new File(baseDir));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        FileUtils.deleteQuietly(new File("mast_parser.lis"));


    }

    @Override
    public void clean() {
        if (workingDirectory != null){
            try {
                FileUtils.deleteDirectory(workingDirectory.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void launchCommand(String command, Long timeoutMilliSeconds, String stdOutputFile, String workingDirectory) throws InterruptedAnalysis {

        // Define executor
        //System.out.print("launch");
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValues(null);  // doesn't check correct exit values
        executor.setWorkingDirectory(new File(workingDirectory));

        //System.out.print("q");

        // Standard Output Handling
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(streamHandler);

        //System.out.print("w");

        ExecuteWatchdog dog = null;
        if (timeoutMilliSeconds != null) {
            // Define watchdog (for timeout)
            dog = new ExecuteWatchdog(timeoutMilliSeconds);
            //System.out.print("e");
            executor.setWatchdog(dog);
            //System.out.print("r");
        }

        // Execute command
        try {
            //System.out.print("t");
            int exitValue = executor.execute(commandLine);
            //System.out.print("x");
        } catch (Exception e) {
            //System.out.print("A");
            //System.out.println(e.getMessage());
            throw new InterruptedAnalysis();
        }
//        catch (IOException e2) {
//            System.out.print("B");
//            throw new InterruptedAnalysis();
//        }

        System.out.print("+");

        try {
            if (stdOutputFile != null) {
                // Save standard output of the process
                OutputStream fileOutput = new FileOutputStream(stdOutputFile);
                outputStream.writeTo(fileOutput);
                outputStream.close();
                fileOutput.close();
            }
        } catch (IOException e) {
            //System.out.print("C");
            throw new InterruptedAnalysis();
        }

        if (dog != null) {
            if (dog.killedProcess()) {
                //System.out.print("D");
                throw new InterruptedAnalysis();
            }
        }
    }

    private void integrateMASTResults(File results, LinearSystem system){

        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(REALTIMESITUATION.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            REALTIMESITUATION element = (REALTIMESITUATION) jaxbUnmarshaller.unmarshal(results);

            Integer flowID;
            String taskID;
            Double bcrt;
            Double wcrt;
            Double jitter;

            for (Object o: element.getSlackOrTraceOrProcessingResource()) {
                if (o instanceof TransactionResults) {
                    TransactionResults tr = (TransactionResults) o;

                    flowID = Integer.valueOf(tr.getName().replaceAll("\\D+",""));

                    for (Object to: tr.getSlackOrTimingResultOrSimulationTimingResult()) {
                        if (to instanceof TimingResult) {
                            TimingResult timing = (TimingResult) to;

                            taskID = String.format("%s", timing.getEventName().replaceAll("\\D+",""));
                            bcrt = timing.getBestGlobalResponseTimes().getGlobalResponseTime().get(0).getTimeValue();
                            wcrt = timing.getWorstGlobalResponseTimes().getGlobalResponseTime().get(0).getTimeValue();
                            jitter = timing.getJitters().getGlobalResponseTime().get(0).getTimeValue();
                            system.setTaskResults(flowID, taskID, bcrt, wcrt, jitter);

                        }
                    }
                }
            }


        } catch (JAXBException e) {
            //System.out.print("E");
            System.out.println("Error in results file: "+results.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private void createHospaParamsFile(File file, HOSPAConfig config){
        try {
            FileOutputStream o = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(o);

            pw.format("HOSPA_Parameters (\n");
            pw.format("Initial_Assignment      => %s,\n", config.getInit().toString());
            pw.format("Size_Of_K_List          =>  1,\n");
            pw.format(Locale.US, "Ka_List                 => (%f),\n", config.getKa());
            pw.format(Locale.US, "Kr_List                 => (%f),\n", config.getKr());
            pw.format("Size_Of_Iterations_List =>  1,\n");
            pw.format("Iterations_List         => (%d),\n", config.getIterations());
            pw.format("Iterations_To_Optimize  =>  %d,\n", config.getOverIterations());

            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }
}
