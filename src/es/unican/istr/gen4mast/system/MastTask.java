package es.unican.istr.gen4mast.system;

import es.unican.istr.rtgen.system.Task;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Created by juanm on 11/08/2015.
 */
public class MastTask extends Task {

    @Override
    public void writeTask(OutputStream o) {
        System.out.println("MAST Task Class");
    }

    public void writeOperation(PrintWriter pw) {

        pw.format("Operation (\n");
        pw.format("     Type     => Simple,\n");
        pw.format("     Name     => O_%s,\n", getId());
        pw.format(Locale.US, "     Worst_Case_Execution_Time     => %f,\n", getWcet());
        pw.format(Locale.US, "     Best_Case_Execution_Time     => %f);\n\n", getBcet());

    }

    public void writeSchedulingServer(PrintWriter pw){

        pw.format("Scheduling_Server (\n");
        pw.format("        Type                       => Regular,\n");
        pw.format("        Name    => SS_%s,\n", getId());
        pw.format("        Server_Sched_Parameters         => (\n");

        if (getProcessor().getSchedulingPolicy().equals("FP")) {
            pw.format("                Type                    => Fixed_Priority_policy,\n");
            pw.format("                The_Priority            => %d,\n", getPriority());
        } else if (getProcessor().getSchedulingPolicy().equals("EDF")) {
            pw.format("                Type                    => EDF_policy,\n");
            pw.format(Locale.US, "                Deadline                => %f,\n", getSchedulingDeadline());
        } else {
            throw new IllegalArgumentException("Scheduling policy "+ getProcessor().getSchedulingPolicy() +" not valid");
        }

        pw.format("                Preassigned             => No),\n");
        pw.format("        Scheduler      => s%d);\n\n", getProcessor().getId());

    }

}
