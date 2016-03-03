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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Rate_Divisor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Rate_Divisor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Rate_Factor" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Positive" />
 *       &lt;attribute name="Input_Event" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *       &lt;attribute name="Output_Event" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rate_Divisor")
public class RateDivisor {

    @XmlAttribute(name = "Rate_Factor", required = true)
    protected BigInteger rateFactor;
    @XmlAttribute(name = "Input_Event", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String inputEvent;
    @XmlAttribute(name = "Output_Event", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String outputEvent;

    /**
     * Gets the value of the rateFactor property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRateFactor() {
        return rateFactor;
    }

    /**
     * Sets the value of the rateFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRateFactor(BigInteger value) {
        this.rateFactor = value;
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

}