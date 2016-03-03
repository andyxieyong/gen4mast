package es.unican.istr.gen4mast.system;

import es.unican.istr.rtgen.system.Flow;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by juanm on 11/08/2015.
 */
public class MastFlow extends Flow {

    @Override
    public void writeFlow(OutputStream o) {
        System.out.println("MAST Flow Class");
    }

    public void writeOperations(PrintWriter pw) {
        for (MastTask mt: (List<MastTask>) getTasks()) {
            mt.writeOperation(pw);
        }
    }

    public void writeSchedulingServers(PrintWriter pw) {
        for (MastTask mt: (List<MastTask>) getTasks()) {
            mt.writeSchedulingServer(pw);
        }
    }

    public void writeTransaction(PrintWriter pw) {
        pw.format("Transaction (\n");
        pw.format("      Type              => Regular,\n");
        pw.format("      Name              => T_%d,\n", getId());
        pw.format("      External_Events   => (\n");
        pw.format("                 (  Type        => Periodic,\n");
        pw.format("                    Name        => EE_%d,\n", getId());
        pw.format(Locale.US, "                    Period      => %f,\n", getPeriod());
        pw.format("                    Max_Jitter  => 0,\n");
        pw.format("                    Phase       => 0)),\n\n");

        pw.format("      Internal_Events   => (\n");
        Iterator<MastTask> iterator = ((List<MastTask>) getTasks()).iterator();
        while (true) {
            pw.format("              (Type     => Regular,\n");
            pw.format("               Name     => IE_%s", iterator.next().getId());
            if (iterator.hasNext()){
                pw.format("),\n");
            } else {
                pw.format(",\n");
                pw.format("               Timing_Requirements      => (\n");
                pw.format("                           Type         => Hard_Global_Deadline,\n");
                pw.format(Locale.US, "                           Deadline     => %f,\n", getDeadline());
                pw.format("                           Referenced_Event     => EE_%d))),\n\n", getId());
                break;
            }
        }

        Iterator<MastTask> iterator2 = ((List<MastTask>) getTasks()).iterator(); //not possible to rewind iterator
        String prevID = String.format("EE_%d", getId());
        pw.format("      Event_Handlers            => (\n");
        MastTask mt = iterator2.next();
        while (true) {
            pw.format("              (Type                => Activity,\n");
            pw.format("               Input_Event         => %s,\n", prevID);
            prevID = String.format("IE_%s", mt.getId());
            pw.format("               Output_Event        => IE_%s,\n", mt.getId());
            pw.format("               Activity_Operation  => O_%s,\n", mt.getId());
            pw.format("               Activity_Server     => SS_%s)", mt.getId());
            if (iterator2.hasNext()) {
                pw.format(",\n");
                mt = iterator2.next();
            } else {
                pw.format("\n");
                break;
            }
        }
        pw.format("         )\n");

        pw.format(");\n\n");
    }
}
