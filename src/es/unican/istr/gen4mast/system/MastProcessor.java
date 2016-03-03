package es.unican.istr.gen4mast.system;

import es.unican.istr.rtgen.system.Processor;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by juanm on 11/08/2015.
 */
public class MastProcessor extends Processor {

    @Override
    public void writeProcessor(OutputStream o) {
        System.out.println("MAST Processor Class");
    }

    public void writeProcessingResource(PrintWriter pw){

        pw.format("Processing_Resource (\n");
        pw.format("        Type                    => Regular_Processor,\n");
        pw.format("        Name                    => CPU_%d,\n", getId());
        pw.format("        Max_Interrupt_Priority  => 32767,\n");
        pw.format("        Min_Interrupt_Priority  => 32767);\n");

    }

    public void writeScheduler(PrintWriter pw){

        pw.format("Scheduler (\n");
        pw.format("   Type            => Primary_Scheduler,\n");
        pw.format("   Name            => s%d,\n", getId());
        pw.format("   Host            => CPU_%d,\n", getId());

        if (getSchedulingPolicy().equals("FP")) {
            pw.format("   Policy          =>      (\n");
            pw.format("        Type                    => Fixed_Priority,\n");
            pw.format("        Max_Priority            => 32766,\n");
            pw.format("        Min_Priority            => 1));\n");
        } else if (getSchedulingPolicy().equals("EDF")) {
            pw.format("   Policy          =>      ( Type                 => EDF));\n");
        } else {
            throw new IllegalArgumentException("Scheduling policy "+ getSchedulingPolicy() +" not valid");
        }
    }
}
