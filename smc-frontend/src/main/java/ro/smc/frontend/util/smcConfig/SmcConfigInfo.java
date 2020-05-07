package ro.smc.frontend.util.smcConfig;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class SmcConfigInfo {
	private final static String FILE_NAME = "/smcConfig.xml";
	private static JAXBContext _context;
	private static SmcConfig _smcConfig;

	private static JAXBContext getContext() throws JAXBException {
		if (_context == null)
			_context = JAXBContext.newInstance(SmcConfig.class);

		return _context;
	}

	public static SmcConfig getSmcConfig() throws JAXBException, IOException {
		if (_smcConfig == null) {
			InputStream is = SmcConfig.class.getClassLoader().getResourceAsStream(FILE_NAME);
			_smcConfig = (SmcConfig) getContext().createUnmarshaller().unmarshal(is);
			is.close();
		}

		return _smcConfig;
	}
}
