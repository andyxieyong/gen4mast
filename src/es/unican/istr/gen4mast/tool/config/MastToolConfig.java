package es.unican.istr.gen4mast.tool.config;

import es.unican.istr.rtgen.exceptions.ConfigurableOptionNotSet;
import es.unican.istr.rtgen.tool.config.ToolConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juanm on 19/08/2015.
 */
public class MastToolConfig extends ToolConfig {

    private String name;
    private String workPath;
    private String mastPath;
    private AnalysisOptions analysis;
    private Boolean sync;
    private AssignmentOptions assignment;
    private HOSPAConfig hospaConfig;
    private Float stopFactor;
    private Boolean gsd;        //Forces Global Scheduling Deadlines in local clock EDF
    private Integer dsFactor;     //Scaling factor for LC-EDF-DS
    private Boolean calculateSlack;
    private Boolean jitterAvoidance;
    private Long analysisTimeout;


    private void init(MastToolConfigurableMap map){
        HOSPAConfig h = new HOSPAConfig();
        for (MastToolConfigurableOptions o: map.keySet()){
            String value = map.get(o).trim();
            switch (o) {
                case NAME: this.setName(value); break;
                case WORK_PATH: this.setWorkPath(value); break;
                case MAST_PATH: this.setMastPath(value); break;
                case ANALYSIS_TOOL: this.setAnalysis(AnalysisOptions.valueOf(value)); break;
                case SYNC: this.setSync(Boolean.parseBoolean(value)); break;
                case ASSIGNMENT_TOOL: this.setAssignment(AssignmentOptions.valueOf(value)); break;
                case HOSPA_INIT: h.setInit(HOSPAConfig.InitOptions.valueOf(value)); break;
                case HOSPA_KA: h.setKa(Float.parseFloat(value)); break;
                case HOSPA_KR: h.setKr(Float.parseFloat(value)); break;
                case HOSPA_ITERATIONS: h.setIterations(Integer.parseInt(value)); break;
                case HOSPA_OVERITERATIONS: h.setOverIterations(Integer.parseInt(value)); break;
                case ANALYSIS_STOP_FACTOR: this.setStopFactor(Float.parseFloat(value)); break;
                case LC_EDF_GSD: ; this.setGsd(Boolean.parseBoolean(value));break;
                case LC_EDF_DS_FACTOR: this.setDsFactor(Integer.parseInt(value)); break;
                case CALCULATE_SLACK: this.setCalculateSlack(Boolean.parseBoolean(value)); break;
                case JITTER_AVOIDANCE: this.setJitterAvoidance(Boolean.parseBoolean(value)); break;
                case ANALYSIS_TIMEOUT: this.setAnalysisTimeout(Long.parseLong(value)); break;
            }
        }
        this.setHospaConfig(h);
    }

    public MastToolConfig(MastToolConfigurableMap map) {
        this.init(map);
    }

    public void initWihArgs(String[] pairs){
        Map<MastToolConfigurableOptions, String> input = new HashMap<>();

        for (String pair: pairs){
            MastToolConfigurableOptions key = MastToolConfigurableOptions.valueOf(pair.split("=")[0].toUpperCase());
            String value = pair.split("=")[1];
            input.put(key, value);
        }

        try{
            MastToolConfigurableMap m = new MastToolConfigurableMap(input);
            this.init(m);
        } catch (ConfigurableOptionNotSet configurableOptionNotSet){
            configurableOptionNotSet.printStackTrace();
            throw new RuntimeException();
        }
    }

    public String[] getConfigAsArgs(){
        MastToolConfigurableMap m = new MastToolConfigurableMap();
        String value = null;
        for (MastToolConfigurableOptions o : MastToolConfigurableOptions.values()){
            switch (o){
                case NAME:                  value = this.name; break;
                case WORK_PATH:             value = this.workPath; break;
                case MAST_PATH:             value = this.mastPath; break;
                case ANALYSIS_TOOL:         value = this.analysis.name(); break;
                case SYNC:                  value = this.sync.toString(); break;
                case ASSIGNMENT_TOOL:       value = this.assignment.name(); break;
                case HOSPA_INIT:            value = this.hospaConfig.getInit().name(); break;
                case HOSPA_KA:              value = this.hospaConfig.getKa().toString(); break;
                case HOSPA_KR:              value = this.hospaConfig.getKr().toString(); break;
                case HOSPA_ITERATIONS:      value = this.hospaConfig.getIterations().toString(); break;
                case HOSPA_OVERITERATIONS:  value = this.hospaConfig.getOverIterations().toString(); break;
                case ANALYSIS_STOP_FACTOR:  value = this.stopFactor.toString(); break;
                case LC_EDF_GSD:            value = this.gsd.toString(); break;
                case LC_EDF_DS_FACTOR:      value = this.dsFactor.toString(); break;
                case CALCULATE_SLACK:       value = this.calculateSlack.toString(); break;
                case JITTER_AVOIDANCE:      value = this.jitterAvoidance.toString(); break;
                case ANALYSIS_TIMEOUT:      value = this.analysisTimeout.toString(); break;
            }
            String valueTrimemed = value.trim();
            m.put(o, valueTrimemed);
        }

        // Create list of strings, then convert it to array of strings
        List<String> l = new ArrayList<>();
        for (MastToolConfigurableOptions o: MastToolConfigurableOptions.values()){
            l.add(String.format("%s=%s", o.name(), m.get(o)));
        }
        return l.stream().toArray(String[]::new);
    }

    public MastToolConfig(String name, String workPath, String mastPath, AnalysisOptions analysis, Boolean sync, AssignmentOptions assignment, HOSPAConfig hospaConfig, Float stopFactor, Boolean gsd, Integer dsFactor, Boolean calculateSlack, Boolean jitterAvoidance, Long analysisTimeout) {
        this.name = name;
        this.workPath = workPath;
        this.mastPath = mastPath;
        this.analysis = analysis;
        this.sync = sync;
        this.assignment = assignment;
        this.hospaConfig = hospaConfig;
        this.stopFactor = stopFactor;
        this.gsd = gsd;
        this.dsFactor = dsFactor;
        this.calculateSlack = calculateSlack;
        this.jitterAvoidance = jitterAvoidance;
        this.analysisTimeout = analysisTimeout;
    }

    public MastToolConfig() {
        super();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkPath() {
        return workPath;
    }

    public void setWorkPath(String workPath) {
        this.workPath = workPath;
    }

    public String getMastPath() {
        return mastPath;
    }

    public void setMastPath(String mastPath) {
        this.mastPath = mastPath;
    }

    public AnalysisOptions getAnalysis() {
        return analysis;
    }

    public void setAnalysis(AnalysisOptions analysis) {
        this.analysis = analysis;
    }

    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }

    public AssignmentOptions getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentOptions assignment) {
        this.assignment = assignment;
    }

    public HOSPAConfig getHospaConfig() {
        return hospaConfig;
    }

    public void setHospaConfig(HOSPAConfig hospaConfig) {
        this.hospaConfig = hospaConfig;
    }

    public Float getStopFactor() {
        return stopFactor;
    }

    public void setStopFactor(Float stopFactor) {
        this.stopFactor = stopFactor;
    }

    public Boolean getGsd() {
        return gsd;
    }

    public void setGsd(Boolean gsd) {
        this.gsd = gsd;
    }

    public Integer getDsFactor() {
        return dsFactor;
    }

    public void setDsFactor(Integer dsFactor) {
        this.dsFactor = dsFactor;
    }

    public Boolean getCalculateSlack() {
        return calculateSlack;
    }

    public void setCalculateSlack(Boolean calculateSlack) {
        this.calculateSlack = calculateSlack;
    }

    public Boolean getJitterAvoidance() {
        return jitterAvoidance;
    }

    public void setJitterAvoidance(Boolean jitterAvoidance) {
        this.jitterAvoidance = jitterAvoidance;
    }

    public Long getAnalysisTimeout() {
        return analysisTimeout;
    }

    public void setAnalysisTimeout(Long analysisTimeout) {
        this.analysisTimeout = analysisTimeout;
    }
}
