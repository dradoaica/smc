//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.23 at 09:32:48 PM EEST 
//


package ro.smc.engine.util.serialization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PrimaryKeys"/>
 *       &lt;/sequence>
 *       &lt;attribute name="configurationName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="relationName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fragmentName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "primaryKeys"
})
@XmlRootElement(name = "ExternalConfigurationKey")
public class ExternalConfigurationKey {

    @XmlElement(name = "PrimaryKeys", required = true)
    protected PrimaryKeys primaryKeys;
    @XmlAttribute(name = "configurationName", required = true)
    protected String configurationName;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "relationName", required = true)
    protected String relationName;
    @XmlAttribute(name = "fragmentName", required = true)
    protected String fragmentName;

    /**
     * Gets the value of the primaryKeys property.
     * 
     * @return
     *     possible object is
     *     {@link PrimaryKeys }
     *     
     */
    public PrimaryKeys getPrimaryKeys() {
        return primaryKeys;
    }

    /**
     * Sets the value of the primaryKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrimaryKeys }
     *     
     */
    public void setPrimaryKeys(PrimaryKeys value) {
        this.primaryKeys = value;
    }

    /**
     * Gets the value of the configurationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigurationName() {
        return configurationName;
    }

    /**
     * Sets the value of the configurationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigurationName(String value) {
        this.configurationName = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the relationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationName() {
        return relationName;
    }

    /**
     * Sets the value of the relationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationName(String value) {
        this.relationName = value;
    }

    /**
     * Gets the value of the fragmentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFragmentName() {
        return fragmentName;
    }

    /**
     * Sets the value of the fragmentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFragmentName(String value) {
        this.fragmentName = value;
    }

}
