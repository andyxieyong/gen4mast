package es.unican.istr.rtgen.system;

import es.unican.istr.rtgen.system.config.localization.LocalizationOptions;
import es.unican.istr.rtgen.system.config.deadline.DeadlineConfig;
import es.unican.istr.rtgen.system.config.period.PeriodConfig;
import es.unican.istr.rtgen.utils.Utils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

/**
 * Created by juanm on 11/08/2015.
 */
public abstract class Flow<T extends Task> {

    private Integer id;

    private Double period;
    private Double deadline;
    private List<T> tasks;

    public Flow() {
        tasks = new ArrayList<>();
    }

    public void addTask(T aTask){
        tasks.add(aTask);
        aTask.setFlow(this);
        aTask.setId(String.format("%d%d", id, tasks.indexOf(aTask)+1));
    }

    public void locateTasks(List<Processor> procs, LocalizationOptions type, Random r){

        switch (type) {
            case RANDOM:
                Integer procIndex = 0;
                for (T t: tasks){
                    procIndex = r.nextInt(procs.size());
                    t.setProcessor(procs.get(procIndex));
                }
                break;

            case AVOID_CONSECUTIVE:
                Processor lastProc = null;
                Processor tempProc;
                for (Task t: tasks){
                    while (true){
                        tempProc = procs.get(r.nextInt(procs.size()));
                        if ((tempProc != lastProc) || (procs.size() == 1)){
                            t.setProcessor(tempProc);
                            lastProc = tempProc;
                            break;
                        }
                    }
                }
                break;

            case AVOID_REPETITION:
                ArrayList<Integer> shuffledProcIndexes =
                        new ArrayList<>(Utils.shuffleToList(procs.size(), tasks.size(), r));
                for (int i=0; i<tasks.size(); i++){
                    tasks.get(i).setProcessor(procs.get(shuffledProcIndexes.get(i)));
                }
                break;
        }
    }

    public void setPeriod(PeriodConfig c, Random r){
        switch (c.getDistribution()){
            case UNIFORM:
                period = r.nextDouble()*(c.getBase()*c.getRatio()-c.getBase())+c.getBase();  break;
            case LOG_UNIFORM:
                Double low = log(c.getBase());
                Double high = log(c.getBase()*c.getRatio()+c.getBase());
                Double e = r.nextDouble()*(high-low)+low;
                Double p = exp(e);
                period = floor(p/c.getBase())*c.getBase();
                break;
                /*
                    gran = period_base
                    e = numpy.random.uniform(low=numpy.log(period_base),high=numpy.log(period_base*period_ratio+gran), size=len(lengths))
                    periods = numpy.exp(e)
                    periods = numpy.floor(periods / gran) * gran
                    return [float(p) for p in periods]
                 */
        }
    }

    public void setDeadline(DeadlineConfig c, Random r) {
        int n = tasks.size();
        switch (c.getValue()){

            // Extremes of segment
            case T: deadline = period; break;
            case NT: deadline = n*period;  break;
            case NT2: deadline = 2.0*n*period;  break;

            // NT-T segment divided in 4 subsegments
            case Q1: deadline = (period/4.0)*(n+3.0);  break;
            case Q2: deadline = (period/2.0)*(n+1.0);  break;
            case Q3: deadline = (period/4.0)*(3.0*n+1.0);  break;

            // NT-T segment divided in 4 subsegments
            case T1: deadline = (period/3.0)*(n+2.0);  break;
            case T2: deadline = (period/3.0)*(2.0*n+1.0);  break;

            // Random + K factor
            case RANDOM: deadline = r.nextDouble()*(n*period-period)+period;  break;
            case K: deadline = period * c.getValueK();  break;

        }
    }

    public Double getPeriod() {
        return period;
    }

    public Double getDeadline() {
        return deadline;
    }

    public Integer getId() {
        return id;
    }

    public List<T> getTasks() {
        return tasks;
    }

    public void setTasks(List<T> tasks) {
        this.tasks = tasks;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void printOverview() {
        System.out.format("%f : ", period);
        for (Task t: tasks) {
            t.printOverview();
            System.out.printf(" ");
        }
        System.out.printf(": %f\n", deadline);
    }

    public void printResultsOverview() {
        System.out.format("%f : ", period);
        for (Task t: tasks) {
            System.out.printf("%f", t.getWcrt());
            System.out.printf(" ");
        }
        System.out.printf(": %f\n", deadline);
    }

    public void setTaskResults(String taskID, Double bcrt, Double wcrt, Double jitter){
        for (Task t: tasks){
            if (t.getId().toLowerCase().equals(taskID.toLowerCase())) {
                t.setBcrt(bcrt);
                t.setWcrt(wcrt);
                t.setJitter(jitter);
            }
        }
    }

    public Double getFlowWCRT() {
        return this.tasks.get(this.tasks.size()-1).getWcrt();
    }

    // Abstract methods

    public abstract void writeFlow(OutputStream o);

}
