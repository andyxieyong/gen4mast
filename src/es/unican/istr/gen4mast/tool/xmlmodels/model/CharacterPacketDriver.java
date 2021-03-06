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
 * <p>Java class for Character_Packet_Driver complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Character_Packet_Driver">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Message_Partitioning" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Assertion" />
 *       &lt;attribute name="RTA_Overhead_Model" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Overhead_Type" />
 *       &lt;attribute name="Packet_Server" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *       &lt;attribute name="Packet_Send_Operation" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *       &lt;attribute name="Packet_Receive_Operation" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *       &lt;attribute name="Character_Transmission_Time" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Time" />
 *       &lt;attribute name="Character_Server" use="required" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *       &lt;attribute name="Character_Send_Operation" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *       &lt;attribute name="Character_Receive_Operation" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Identifier_Ref" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Character_Packet_Driver")
public class CharacterPacketDriver {

    @XmlAttribute(name = "Message_Partitioning")
    protected Assertion messagePartitioning;
    @XmlAttribute(name = "RTA_Overhead_Model")
    protected OverheadType rtaOverheadModel;
    @XmlAttribute(name = "Packet_Server", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String packetServer;
    @XmlAttribute(name = "Packet_Send_Operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String packetSendOperation;
    @XmlAttribute(name = "Packet_Receive_Operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String packetReceiveOperation;
    @XmlAttribute(name = "Character_Transmission_Time")
    protected Double characterTransmissionTime;
    @XmlAttribute(name = "Character_Server", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String characterServer;
    @XmlAttribute(name = "Character_Send_Operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String characterSendOperation;
    @XmlAttribute(name = "Character_Receive_Operation")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String characterReceiveOperation;

    /**
     * Gets the value of the messagePartitioning property.
     * 
     * @return
     *     possible object is
     *     {@link Assertion }
     *     
     */
    public Assertion getMessagePartitioning() {
        return messagePartitioning;
    }

    /**
     * Sets the value of the messagePartitioning property.
     * 
     * @param value
     *     allowed object is
     *     {@link Assertion }
     *     
     */
    public void setMessagePartitioning(Assertion value) {
        this.messagePartitioning = value;
    }

    /**
     * Gets the value of the rtaOverheadModel property.
     * 
     * @return
     *     possible object is
     *     {@link OverheadType }
     *     
     */
    public OverheadType getRTAOverheadModel() {
        return rtaOverheadModel;
    }

    /**
     * Sets the value of the rtaOverheadModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link OverheadType }
     *     
     */
    public void setRTAOverheadModel(OverheadType value) {
        this.rtaOverheadModel = value;
    }

    /**
     * Gets the value of the packetServer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPacketServer() {
        return packetServer;
    }

    /**
     * Sets the value of the packetServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPacketServer(String value) {
        this.packetServer = value;
    }

    /**
     * Gets the value of the packetSendOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPacketSendOperation() {
        return packetSendOperation;
    }

    /**
     * Sets the value of the packetSendOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPacketSendOperation(String value) {
        this.packetSendOperation = value;
    }

    /**
     * Gets the value of the packetReceiveOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPacketReceiveOperation() {
        return packetReceiveOperation;
    }

    /**
     * Sets the value of the packetReceiveOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPacketReceiveOperation(String value) {
        this.packetReceiveOperation = value;
    }

    /**
     * Gets the value of the characterTransmissionTime property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCharacterTransmissionTime() {
        return characterTransmissionTime;
    }

    /**
     * Sets the value of the characterTransmissionTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCharacterTransmissionTime(Double value) {
        this.characterTransmissionTime = value;
    }

    /**
     * Gets the value of the characterServer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacterServer() {
        return characterServer;
    }

    /**
     * Sets the value of the characterServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacterServer(String value) {
        this.characterServer = value;
    }

    /**
     * Gets the value of the characterSendOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacterSendOperation() {
        return characterSendOperation;
    }

    /**
     * Sets the value of the characterSendOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacterSendOperation(String value) {
        this.characterSendOperation = value;
    }

    /**
     * Gets the value of the characterReceiveOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacterReceiveOperation() {
        return characterReceiveOperation;
    }

    /**
     * Sets the value of the characterReceiveOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacterReceiveOperation(String value) {
        this.characterReceiveOperation = value;
    }

}
