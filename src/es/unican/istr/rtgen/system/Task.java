package es.unican.istr.rtgen.system;

import java.io.OutputStream;

/**
 * Created by juanm on 11/08/2015.
 */
public abstract class Task {

    private String id;

    // Basic Characteristics
    private Double wcet;
    private Double bcet;
    private Double offset;
    private Integer priority;
    private Double schedulingDeadline;
    private Processor processor;
    private Flow flow;

    // Results
    private Double wcrt;
    private Double bcrt;
    private Double jitter;


    // Getters and Setters

    public Double getWcet() {
        return wcet;
    }

    public void setWcet(Double wcet) {
        this.wcet = wcet;
    }

    public Double getBcet() {
        return bcet;
    }

    public void setBcet(Double bcet) {
        this.bcet = bcet;
    }

    public Double getOffset() {
        return offset;
    }

    public void setOffset(Double offset) {
        this.offset = offset;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Double getSchedulingDeadline() {
        return schedulingDeadline;
    }

    public void setSchedulingDeadline(Double schedulingDeadline) {
        this.schedulingDeadline = schedulingDeadline;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
        processor.addTask(this);
    }

    public Double getWcrt() {
        return wcrt;
    }

    public void setWcrt(Double wcrt) {
        this.wcrt = wcrt;
    }

    public Double getBcrt() {
        return bcrt;
    }

    public void setBcrt(Double bcrt) {
        this.bcrt = bcrt;
    }

    public Double getJitter() {
        return jitter;
    }

    public void setJitter(Double jitter) {
        this.jitter = jitter;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void printOverview() {
        System.out.format("(%f)%d", wcet, processor.getId());
    }

    // Abstract methods

    public abstract void writeTask(OutputStream o);

}
