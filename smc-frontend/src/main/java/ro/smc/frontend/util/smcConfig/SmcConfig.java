package ro.smc.frontend.util.smcConfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "smcConfig")
public class SmcConfig {
	@XmlElement(name = "encryption-key", required = true)
	protected String encryptionKey;

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String value) {
		this.encryptionKey = value;
	}
}
