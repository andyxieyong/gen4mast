package es.unican.istr.rtgen.system;

import es.unican.istr.rtgen.system.config.load.WCETGenerationOptions;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;

/**
 * Created by juanm on 11/08/2015.
 */
public abstract class Processor<T extends Task> {

    private Integer id;
    private String schedulingPolicy;
    private List<T> tasks;


    public Processor(){
        tasks = new ArrayList<>();
    }

    public void addTask(T aTask){
        tasks.add(aTask);
    }


    // Utilization generation methods

    public void scaleUtilization(Double factor){
        /*
        Scales the utilization of the processor by a factor of "factor"
         */
        for (Task t: tasks) {
            t.setWcet(t.getWcet()*factor);
        }
    }

    public void setUtilization(WCETGenerationOptions o, Double u, Random r){
        switch (o){
            case SCALE:
                for (Task t: tasks) {
                    t.setWcet(u*t.getFlow().getPeriod()/tasks.size());
                }
                break;

            case UUNIFAST:
                Double sumU = u;
                Double nextSumU;
                for (Task t: tasks) {

                    // Last task in the list
                    if (tasks.indexOf(t)+1 == tasks.size()) {
                        t.setWcet(sumU*t.getFlow().getPeriod());
                        break;
                    }

                    nextSumU = sumU*pow(r.nextDouble(), 1.0/(tasks.size()-tasks.indexOf(t)+1));
                    t.setWcet((sumU-nextSumU)*t.getFlow().getPeriod());
                    sumU = nextSumU;
                }
                break;
        }
    }

    public void setBestCaseUtilization(Float factor) {
        for (T t: tasks) {
            t.setBcet(t.getWcet()*factor);
        }
    }

    public Double getUtilization() {
        Double u = 0.0;
        for (T t: tasks) {
            u += t.getWcet()/t.getFlow().getPeriod();
        }
        return u;
    }

    public void printOverview() {
        System.out.printf("%f", this.getUtilization());
    }

    // Getter and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchedulingPolicy() {
        return schedulingPolicy;
    }

    public void setSchedulingPolicy(String schedulingPolicy) {
        this.schedulingPolicy = schedulingPolicy;
    }


    // Abstract methods

    public abstract void writeProcessor(OutputStream o);

}
