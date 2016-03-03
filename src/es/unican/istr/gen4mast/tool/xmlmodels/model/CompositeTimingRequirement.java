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
 * <p>Java class for Composite_Timing_Requirement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Composite_Timing_Requirement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="Max_Output_Jitter_Req" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Max_Output_Jitter_Req"/>
 *         &lt;element name="Hard_Global_Deadline" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Hard_Global_Deadline"/>
 *         &lt;element name="Soft_Global_Deadline" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Soft_Global_Deadline"/>
 *         &lt;element name="Global_Max_Miss_Ratio" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Global_Max_Miss_Ratio"/>
 *         &lt;element name="Hard_Local_Deadline" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Hard_Local_Deadline"/>
 *         &lt;element name="Soft_Local_Deadline" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Soft_Local_Deadline"/>
 *         &lt;element name="Local_Max_Miss_Ratio" type="{http://mast.unican.es/xmlmast/xmlmast_1_4/Mast_Model}Local_Max_Miss_Ratio"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Composite_Timing_Requirement", propOrder = {
    "maxOutputJitterReqOrHardGlobalDeadlineOrSoftGlobalDeadline"
})
public class CompositeTimingRequirement {

    @XmlElements({
        @XmlElement(name = "Max_Output_Jitter_Req", type = MaxOutputJitterReq.class),
        @XmlElement(name = "Hard_Global_Deadline", type = HardGlobalDeadline.class),
        @XmlElement(name = "Soft_Global_Deadline", type = SoftGlobalDeadline.class),
        @XmlElement(name = "Global_Max_Miss_Ratio", type = GlobalMaxMissRatio.class),
        @XmlElement(name = "Hard_Local_Deadline", type = HardLocalDeadline.class),
        @XmlElement(name = "Soft_Local_Deadline", type = SoftLocalDeadline.class),
        @XmlElement(name = "Local_Max_Miss_Ratio", type = LocalMaxMissRatio.class)
    })
    protected List<Object> maxOutputJitterReqOrHardGlobalDeadlineOrSoftGlobalDeadline;

    /**
     * Gets the value of the maxOutputJitterReqOrHardGlobalDeadlineOrSoftGlobalDeadline property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maxOutputJitterReqOrHardGlobalDeadlineOrSoftGlobalDeadline property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaxOutputJitterReqOrHardGlobalDeadlineOrSoftGlobalDeadline().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaxOutputJitterReq }
     * {@link HardGlobalDeadline }
     * {@link SoftGlobalDeadline }
     * {@link GlobalMaxMissRatio }
     * {@link HardLocalDeadline }
     * {@link SoftLocalDeadline }
     * {@link LocalMaxMissRatio }
     * 
     * 
     */
    public List<Object> getMaxOutputJitterReqOrHardGlobalDeadlineOrSoftGlobalDeadline() {
        if (maxOutputJitterReqOrHardGlobalDeadlineOrSoftGlobalDeadline == null) {
            maxOutputJitterReqOrHardGlobalDeadlineOrSoftGlobalDeadline = new ArrayList<Object>();
        }
        return this.maxOutputJitterReqOrHardGlobalDeadlineOrSoftGlobalDeadline;
    }

}