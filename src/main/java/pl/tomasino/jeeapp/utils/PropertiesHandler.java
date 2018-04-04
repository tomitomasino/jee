package pl.tomasino.jeeapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.Dependent;

@Dependent
public class PropertiesHandler {

	InputStream input = null;
	final static String propFileName = "config.properties";

	public String getProperty(String p) {
		try {
			Properties prop = new Properties();
			input = getClass().getClassLoader().getResourceAsStream(propFileName);
			prop.load(input);
			return prop.getProperty(p);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
