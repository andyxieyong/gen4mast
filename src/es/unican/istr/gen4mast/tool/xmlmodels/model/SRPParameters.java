//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.12 at 01:08:01 PM CET 
//


package es.unican.istr.gen4mast.tool.xmlmodels.model;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SRP_Parameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SRP_Parameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Preemption_Level" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Preemption_Level" />
 *       &lt;attribute name="Preassigned" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Assertion" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SRP_Parameters")
public class SRPParameters {

    @XmlAttribute(name = "Preemption_Level", required = true)
    protected BigInteger preemptionLevel;
    @XmlAttribute(name = "Preassigned")
    protected Assertion preassigned;

    /**
     * Gets the value of the preemptionLevel property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPreemptionLevel() {
        return preemptionLevel;
    }

    /**
     * Sets the value of the preemptionLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPreemptionLevel(BigInteger value) {
        this.preemptionLevel = value;
    }

    /**
     * Gets the value of the preassigned property.
     * 
     * @return
     *     possible object is
     *     {@link Assertion }
     *     
     */
    public Assertion getPreassigned() {
        return preassigned;
    }

    /**
     * Sets the value of the preassigned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Assertion }
     *     
     */
    public void setPreassigned(Assertion value) {
        this.preassigned = value;
    }

}