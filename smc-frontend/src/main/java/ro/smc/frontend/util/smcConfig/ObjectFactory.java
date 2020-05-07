package ro.smc.frontend.util.smcConfig;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
	public ObjectFactory() {
	}

	public SmcConfig createSmcConfig() {
		return new SmcConfig();
	}
}
