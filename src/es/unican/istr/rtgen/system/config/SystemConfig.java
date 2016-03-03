package es.unican.istr.rtgen.system.config;

import es.unican.istr.rtgen.exceptions.ConfigurableOptionNotSet;
import es.unican.istr.rtgen.system.config.load.UtilizationConfig;
import es.unican.istr.rtgen.system.config.deadline.DeadlineConfig;
import es.unican.istr.rtgen.system.config.load.LoadBalancingOptions;
import es.unican.istr.rtgen.system.config.load.WCETGenerationOptions;
import es.unican.istr.rtgen.system.config.localization.LocalizationOptions;
import es.unican.istr.rtgen.system.config.period.PeriodConfig;
import es.unican.istr.rtgen.system.config.period.PeriodDistributionOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.String;

/**
 * Created by juanm on 17/08/2015.
 */
public class SystemConfig {

    private Integer seed;
    private Integer nProcs;
    private Integer nFlows;
    private Integer nTasks;
    private Boolean randomLength;
    private Float singleFlows;
    private String schedPolicy;

    private PeriodConfig period;
    private DeadlineConfig deadline;
    private LocalizationOptions localization;
    private UtilizationConfig utilization;


    public SystemConfig(LinearSystemConfigurableMap map){
        this.init(map);
    }

    private void init(LinearSystemConfigurableMap map){
        PeriodConfig p = new PeriodConfig();
        UtilizationConfig u = new UtilizationConfig();
        DeadlineConfig deadline;

        for (LinearSystemConfigurableOptions o: map.keySet()){
            String value = map.get(o).trim();
            switch (o.name()){
                case "SEED": this.setSeed(Integer.parseInt(value));break;
                case "N_PROCESSORS": this.setnProcs(Integer.parseInt(value)); break;
                case "N_FLOWS": this.setnFlows(Integer.parseInt(value)); break;
                case "N_TASKS": this.setnTasks(Integer.parseInt(value)); break;
                case "RANDOM_LENGTH": this.setRandomLength(Boolean.parseBoolean(value)); break;
                case "SINGLE_FLOWS": this.setSingleFlows(Float.parseFloat(value)); break;
                case "SCHED_POLICY": this.setSchedPolicy(value); break;
                case "PERIOD_DISTRIBUTION": p.setDistribution(PeriodDistributionOptions.valueOf(value)); break;
                case "PERIOD_BASE": p.setBase(Float.parseFloat(value));break;
                case "PERIOD_RATIO": p.setRatio(Float.parseFloat(value)); break;
                case "DEADLINE": deadline = new DeadlineConfig(value.trim()); this.setDeadline(deadline);break;
                case "TASK_LOCALIZATION": this.setLocalization(LocalizationOptions.valueOf(value)); break;
                case "UTILIZATION": u.setUtilization(Integer.parseInt(value)); break;
                case "UTILIZATION_BCET_FACTOR": u.setBcetFactor(Float.parseFloat(value)); break;
                case "UTILIZATION_WCET_METHOD": u.setWcetMethod(WCETGenerationOptions.valueOf(value)); break;
                case "UTILIZATION_BALANCING": u.setBalancing(LoadBalancingOptions.valueOf(value)); break;
            }
        }

        this.setPeriod(p);
        this.setUtilization(u);
    }

    public void initWihArgs(String[] pairs){
        Map<LinearSystemConfigurableOptions, String> input = new HashMap<>();

        for (String pair: pairs){
            LinearSystemConfigurableOptions key = LinearSystemConfigurableOptions.valueOf(pair.split("=")[0].toUpperCase());
            String value = pair.split("=")[1].toUpperCase();
            input.put(key, value);
        }

        try {
            LinearSystemConfigurableMap m = new LinearSystemConfigurableMap(input);
            this.init(m);
        } catch (ConfigurableOptionNotSet configurableOptionNotSet) {
            configurableOptionNotSet.printStackTrace();
            throw new RuntimeException();
        }
    }

    public String[] getConfigAsArgs(){
        LinearSystemConfigurableMap m = new LinearSystemConfigurableMap();
        String value;
        for (LinearSystemConfigurableOptions o: LinearSystemConfigurableOptions.values()){
            switch (o){
                case SEED:                  value = this.seed.toString(); break;
                case N_PROCESSORS:          value = this.nProcs.toString(); break;
                case N_FLOWS:               value = this.nFlows.toString(); break;
                case N_TASKS:               value = this.nTasks.toString(); break;
                case RANDOM_LENGTH:         value = this.randomLength.toString(); break;
                case SINGLE_FLOWS:          value = this.singleFlows.toString(); break;
                case SCHED_POLICY:          value = this.schedPolicy; break;
                case PERIOD_DISTRIBUTION:   value = this.period.getDistribution().name(); break;
                case PERIOD_BASE:           value = this.period.getBase().toString(); break;
                case PERIOD_RATIO:          value = this.period.getRatio().toString(); break;
                case DEADLINE:              value = this.deadline.getDeadlineAsString(); break;
                case TASK_LOCALIZATION:     value = this.localization.name(); break;
                case UTILIZATION:           value = this.utilization.getUtilization().toString(); break;
                case UTILIZATION_BCET_FACTOR: value = this.utilization.getBcetFactor().toString(); break;
                case UTILIZATION_WCET_METHOD: value = this.utilization.getWcetMethod().name(); break;
                case UTILIZATION_BALANCING:   value = this.utilization.getBalancing().name(); break;
                default:                    value = "";
            }
            String valueTrimmed = value.trim();
            m.put(o, valueTrimmed);
        }

        // Create list of strings, then convert it to array of strings
        List<String> l = new ArrayList<>();
        for (LinearSystemConfigurableOptions o: LinearSystemConfigurableOptions.values()){
            l.add(String.format("%s=%s", o.name(), m.get(o)));
        }
        return l.stream().toArray(String[]::new);

    }

    public SystemConfig(Integer theSeed, Integer nProcs, Integer nFlows, Integer nTasks, Boolean randomLength, Float singleFlows, String schedPolicy, PeriodConfig period, DeadlineConfig deadline, LocalizationOptions localization, UtilizationConfig utilization) {
        this.seed = theSeed;
        this.nProcs = nProcs;
        this.nFlows = nFlows;
        this.nTasks = nTasks;
        this.randomLength = randomLength;
        this.singleFlows = singleFlows;
        this.schedPolicy = schedPolicy;
        this.period = period;
        this.deadline = deadline;
        this.localization = localization;
        this.utilization = utilization;
    }

    public SystemConfig() {
        super();
    }


    /////////////////////////
    // Getters and Setters //
    /////////////////////////

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public Integer getnProcs() {
        return nProcs;
    }

    public void setnProcs(Integer nProcs) {
        this.nProcs = nProcs;
    }

    public Integer getnFlows() {
        return nFlows;
    }

    public void setnFlows(Integer nFlows) {
        this.nFlows = nFlows;
    }

    public Integer getnTasks() {
        return nTasks;
    }

    public void setnTasks(Integer nTasks) {
        this.nTasks = nTasks;
    }

    public Float getSingleFlows() {
        return singleFlows;
    }

    public Boolean getRandomLength() {
        return randomLength;
    }

    public void setRandomLength(Boolean randomLength) {
        this.randomLength = randomLength;
    }

    public void setSingleFlows(Float singleFlows) {
        this.singleFlows = singleFlows;
    }

    public String getSchedPolicy() {
        return schedPolicy;
    }

    public void setSchedPolicy(String schedPolicy) {
        this.schedPolicy = schedPolicy;
    }

    public PeriodConfig getPeriod() {
        return period;
    }

    public void setPeriod(PeriodConfig period) {
        this.period = period;
    }

    public DeadlineConfig getDeadline() {
        return deadline;
    }

    public void setDeadline(DeadlineConfig deadline) {
        this.deadline = deadline;
    }

    public LocalizationOptions getLocalization() {
        return localization;
    }

    public void setLocalization(LocalizationOptions localization) {
        this.localization = localization;
    }

    public UtilizationConfig getUtilization() {
        return utilization;
    }

    public void setUtilization(UtilizationConfig utilization) {
        this.utilization = utilization;
    }

}
