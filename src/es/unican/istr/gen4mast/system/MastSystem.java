package es.unican.istr.gen4mast.system;

import es.unican.istr.rtgen.system.config.SystemConfig;
import es.unican.istr.rtgen.system.Flow;
import es.unican.istr.rtgen.system.LinearSystem;
import es.unican.istr.rtgen.system.Processor;
import es.unican.istr.rtgen.system.Task;

import java.io.*;
import java.util.List;

/**
 * Created by juanm on 11/08/2015.
 */
public class MastSystem<T extends Task, F extends Flow, P extends Processor> extends LinearSystem {

    public MastSystem(Class<T> t, Class<F> f, Class<P> p, SystemConfig systemConfiguration) {
        super(t, f, p, systemConfiguration);
    }

    public MastSystem(SystemConfig systemConfiguration) {
        super(MastTask.class, MastFlow.class, MastProcessor.class, systemConfiguration);
    }

    @Override
    public void writeSystem(File f) {
        try {
            FileOutputStream o = new FileOutputStream(f);
            PrintWriter pw = new PrintWriter(o);

            // Processing Resources
            for (MastProcessor p: (List<MastProcessor>) getProcessors()) {
                p.writeProcessingResource(pw);
                pw.write("\n");
            }
            pw.write("\n");

            // Schedulers
            for (MastProcessor p: (List<MastProcessor>) getProcessors()) {
                p.writeScheduler(pw);
                pw.write("\n");
            }
            pw.write("\n");

            // Operations
            for (MastFlow mf: (List<MastFlow>) getFlows()) {
                mf.writeOperations(pw);
            }
            pw.write("\n");

            // Scheduling Servers (Tasks)
            for (MastFlow mf: (List<MastFlow>) getFlows()) {
                mf.writeSchedulingServers(pw);
            }
            pw.write("\n");

            // Transactions
            for (MastFlow mf: (List<MastFlow>) getFlows()) {
                mf.writeTransaction(pw);
            }

            pw.flush();
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
