//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.23 at 09:32:48 PM EEST 
//


package ro.smc.engine.util.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{}ConfigurationDataItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ConfigurationSqlItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "configurationDataItem",
    "configurationSqlItem"
})
@XmlRootElement(name = "ConfigurationItems")
public class ConfigurationItems {

    @XmlElement(name = "ConfigurationDataItem")
    protected List<ConfigurationDataItem> configurationDataItem;
    @XmlElement(name = "ConfigurationSqlItem")
    protected List<ConfigurationSqlItem> configurationSqlItem;

    /**
     * Gets the value of the configurationDataItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configurationDataItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfigurationDataItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfigurationDataItem }
     * 
     * 
     */
    public List<ConfigurationDataItem> getConfigurationDataItem() {
        if (configurationDataItem == null) {
            configurationDataItem = new ArrayList<ConfigurationDataItem>();
        }
        return this.configurationDataItem;
    }

    /**
     * Gets the value of the configurationSqlItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configurationSqlItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfigurationSqlItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfigurationSqlItem }
     * 
     * 
     */
    public List<ConfigurationSqlItem> getConfigurationSqlItem() {
        if (configurationSqlItem == null) {
            configurationSqlItem = new ArrayList<ConfigurationSqlItem>();
        }
        return this.configurationSqlItem;
    }

}
