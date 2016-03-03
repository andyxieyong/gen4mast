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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Offset complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Offset">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Delay_Max_Interval" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Time" />
 *       &lt;attribute name="Delay_Min_Interval" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Time" />
 *       &lt;attribute name="Input_Event" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *       &lt;attribute name="Output_Event" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *       &lt;attribute name="Referenced_Event" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Offset")
public class Offset {

    @XmlAttribute(name = "Delay_Max_Interval")
    protected Double delayMaxInterval;
    @XmlAttribute(name = "Delay_Min_Interval")
    protected Double delayMinInterval;
    @XmlAttribute(name = "Input_Event", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String inputEvent;
    @XmlAttribute(name = "Output_Event", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String outputEvent;
    @XmlAttribute(name = "Referenced_Event", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String referencedEvent;

    /**
     * Gets the value of the delayMaxInterval property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDelayMaxInterval() {
        return delayMaxInterval;
    }

    /**
     * Sets the value of the delayMaxInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDelayMaxInterval(Double value) {
        this.delayMaxInterval = value;
    }

    /**
     * Gets the value of the delayMinInterval property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDelayMinInterval() {
        return delayMinInterval;
    }

    /**
     * Sets the value of the delayMinInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDelayMinInterval(Double value) {
        this.delayMinInterval = value;
    }

    /**
     * Gets the value of the inputEvent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputEvent() {
        return inputEvent;
    }

    /**
     * Sets the value of the inputEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputEvent(String value) {
        this.inputEvent = value;
    }

    /**
     * Gets the value of the outputEvent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputEvent() {
        return outputEvent;
    }

    /**
     * Sets the value of the outputEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputEvent(String value) {
        this.outputEvent = value;
    }

    /**
     * Gets the value of the referencedEvent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencedEvent() {
        return referencedEvent;
    }

    /**
     * Sets the value of the referencedEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencedEvent(String value) {
        this.referencedEvent = value;
    }

}