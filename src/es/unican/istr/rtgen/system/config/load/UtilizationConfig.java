package es.unican.istr.rtgen.system.config.load;

/**
 * Created by juanm on 17/08/2015.
 */
public class UtilizationConfig {

    private Integer utilization; // Average processor utilization in the system

    private Float bcetFactor; // bcet = wcet*bcetFactor

    private WCETGenerationOptions wcetMethod;
    private LoadBalancingOptions balancing;

    public UtilizationConfig(Integer utilization, Float bcetFactor, WCETGenerationOptions wcetMethod,
                             LoadBalancingOptions balancing) {
        this.utilization = utilization;
        if ((bcetFactor <= 1.0) && (bcetFactor >= 0.0)) {
            this.bcetFactor = bcetFactor;
        } else {
            throw new IllegalArgumentException("BCET factor "+bcetFactor.toString()+ " not valid");
        }
        this.wcetMethod = wcetMethod;
        this.balancing = balancing;
    }

    public UtilizationConfig(){
        super();
    }

    public Integer getUtilization() {
        return utilization;
    }

    public void setUtilization(Integer utilization) {
        this.utilization = utilization;
    }

    public Float getBcetFactor() {
        return bcetFactor;
    }

    public void setBcetFactor(Float bcetFactor) {
        this.bcetFactor = bcetFactor;
    }

    public WCETGenerationOptions getWcetMethod() {
        return wcetMethod;
    }

    public void setWcetMethod(WCETGenerationOptions wcetMethod) {
        this.wcetMethod = wcetMethod;
    }

    public LoadBalancingOptions getBalancing() {
        return balancing;
    }

    public void setBalancing(LoadBalancingOptions balancing) {
        this.balancing = balancing;
    }

}
