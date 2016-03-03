//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.12 at 01:08:01 PM CET 
//


package es.unican.istr.gen4mast.tool.xmlmodels.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Regular_Scheduling_Server complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Regular_Scheduling_Server">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="Interrupt_FP_Policy" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Interrupt_FP_Policy"/>
 *           &lt;element name="Non_Preemptible_FP_Policy" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Non_Preemptible_FP_Policy"/>
 *           &lt;element name="Fixed_Priority_Policy" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Fixed_Priority_Policy"/>
 *           &lt;element name="Polling_Policy" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Polling_Policy"/>
 *           &lt;element name="Sporadic_Server_Policy" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Sporadic_Server_Policy"/>
 *           &lt;element name="EDF_Policy" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}EDF_Policy"/>
 *         &lt;/choice>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="SRP_Parameters" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}SRP_Parameters"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier" />
 *       &lt;attribute name="Scheduler" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Regular_Scheduling_Server", propOrder = {
    "interruptFPPolicy",
    "nonPreemptibleFPPolicy",
    "fixedPriorityPolicy",
    "pollingPolicy",
    "sporadicServerPolicy",
    "edfPolicy",
    "srpParameters"
})
public class RegularSchedulingServer {

    @XmlElement(name = "Interrupt_FP_Policy")
    protected InterruptFPPolicy interruptFPPolicy;
    @XmlElement(name = "Non_Preemptible_FP_Policy")
    protected NonPreemptibleFPPolicy nonPreemptibleFPPolicy;
    @XmlElement(name = "Fixed_Priority_Policy")
    protected FixedPriorityPolicy fixedPriorityPolicy;
    @XmlElement(name = "Polling_Policy")
    protected PollingPolicy pollingPolicy;
    @XmlElement(name = "Sporadic_Server_Policy")
    protected SporadicServerPolicy sporadicServerPolicy;
    @XmlElement(name = "EDF_Policy")
    protected EDFPolicy edfPolicy;
    @XmlElement(name = "SRP_Parameters")
    protected SRPParameters srpParameters;
    @XmlAttribute(name = "Name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String name;
    @XmlAttribute(name = "Scheduler", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String scheduler;

    /**
     * Gets the value of the interruptFPPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link InterruptFPPolicy }
     *     
     */
    public InterruptFPPolicy getInterruptFPPolicy() {
        return interruptFPPolicy;
    }

    /**
     * Sets the value of the interruptFPPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterruptFPPolicy }
     *     
     */
    public void setInterruptFPPolicy(InterruptFPPolicy value) {
        this.interruptFPPolicy = value;
    }

    /**
     * Gets the value of the nonPreemptibleFPPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link NonPreemptibleFPPolicy }
     *     
     */
    public NonPreemptibleFPPolicy getNonPreemptibleFPPolicy() {
        return nonPreemptibleFPPolicy;
    }

    /**
     * Sets the value of the nonPreemptibleFPPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link NonPreemptibleFPPolicy }
     *     
     */
    public void setNonPreemptibleFPPolicy(NonPreemptibleFPPolicy value) {
        this.nonPreemptibleFPPolicy = value;
    }

    /**
     * Gets the value of the fixedPriorityPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link FixedPriorityPolicy }
     *     
     */
    public FixedPriorityPolicy getFixedPriorityPolicy() {
        return fixedPriorityPolicy;
    }

    /**
     * Sets the value of the fixedPriorityPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link FixedPriorityPolicy }
     *     
     */
    public void setFixedPriorityPolicy(FixedPriorityPolicy value) {
        this.fixedPriorityPolicy = value;
    }

    /**
     * Gets the value of the pollingPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link PollingPolicy }
     *     
     */
    public PollingPolicy getPollingPolicy() {
        return pollingPolicy;
    }

    /**
     * Sets the value of the pollingPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link PollingPolicy }
     *     
     */
    public void setPollingPolicy(PollingPolicy value) {
        this.pollingPolicy = value;
    }

    /**
     * Gets the value of the sporadicServerPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link SporadicServerPolicy }
     *     
     */
    public SporadicServerPolicy getSporadicServerPolicy() {
        return sporadicServerPolicy;
    }

    /**
     * Sets the value of the sporadicServerPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link SporadicServerPolicy }
     *     
     */
    public void setSporadicServerPolicy(SporadicServerPolicy value) {
        this.sporadicServerPolicy = value;
    }

    /**
     * Gets the value of the edfPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link EDFPolicy }
     *     
     */
    public EDFPolicy getEDFPolicy() {
        return edfPolicy;
    }

    /**
     * Sets the value of the edfPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link EDFPolicy }
     *     
     */
    public void setEDFPolicy(EDFPolicy value) {
        this.edfPolicy = value;
    }

    /**
     * Gets the value of the srpParameters property.
     * 
     * @return
     *     possible object is
     *     {@link SRPParameters }
     *     
     */
    public SRPParameters getSRPParameters() {
        return srpParameters;
    }

    /**
     * Sets the value of the srpParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link SRPParameters }
     *     
     */
    public void setSRPParameters(SRPParameters value) {
        this.srpParameters = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the scheduler property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduler() {
        return scheduler;
    }

    /**
     * Sets the value of the scheduler property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduler(String value) {
        this.scheduler = value;
    }

}