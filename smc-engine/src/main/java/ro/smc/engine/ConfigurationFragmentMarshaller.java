package ro.smc.engine;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class ConfigurationFragmentMarshaller {
	private static JAXBContext context;

	private static JAXBContext getContext() throws JAXBException {
		if (context == null)
			context = JAXBContext.newInstance(ConfigurationFragment.class);

		return context;
	}

	public static String marshal(ConfigurationFragment configurationFragment) throws JAXBException {
		Marshaller m = getContext().createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();
		m.marshal(configurationFragment, sw);

		return sw.toString();
	}

	public static ConfigurationFragment unmarshal(String xml) throws JAXBException {
		return (ConfigurationFragment) getContext().createUnmarshaller().unmarshal(new StringReader(xml));
	}
}
