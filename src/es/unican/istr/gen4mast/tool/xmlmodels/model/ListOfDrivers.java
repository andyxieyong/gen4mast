//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.12 at 01:08:01 PM CET 
//


package es.unican.istr.gen4mast.tool.xmlmodels.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for List_of_Drivers complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="List_of_Drivers">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="Packet_Driver" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Packet_Driver"/>
 *         &lt;element name="Character_Packet_Driver" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Character_Packet_Driver"/>
 *         &lt;element name="RTEP_Packet_Driver" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}RTEP_Packet_Driver"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "List_of_Drivers", propOrder = {
    "packetDriverOrCharacterPacketDriverOrRTEPPacketDriver"
})
public class ListOfDrivers {

    @XmlElements({
        @XmlElement(name = "Packet_Driver", type = PacketDriver.class),
        @XmlElement(name = "Character_Packet_Driver", type = CharacterPacketDriver.class),
        @XmlElement(name = "RTEP_Packet_Driver", type = RTEPPacketDriver.class)
    })
    protected List<Object> packetDriverOrCharacterPacketDriverOrRTEPPacketDriver;

    /**
     * Gets the value of the packetDriverOrCharacterPacketDriverOrRTEPPacketDriver property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the packetDriverOrCharacterPacketDriverOrRTEPPacketDriver property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPacketDriverOrCharacterPacketDriverOrRTEPPacketDriver().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PacketDriver }
     * {@link CharacterPacketDriver }
     * {@link RTEPPacketDriver }
     * 
     * 
     */
    public List<Object> getPacketDriverOrCharacterPacketDriverOrRTEPPacketDriver() {
        if (packetDriverOrCharacterPacketDriverOrRTEPPacketDriver == null) {
            packetDriverOrCharacterPacketDriverOrRTEPPacketDriver = new ArrayList<Object>();
        }
        return this.packetDriverOrCharacterPacketDriverOrRTEPPacketDriver;
    }

}