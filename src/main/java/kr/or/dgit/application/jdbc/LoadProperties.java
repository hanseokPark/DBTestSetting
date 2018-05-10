package kr.or.dgit.application.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
	private Properties properties;

	public LoadProperties() {
		properties = new Properties();
		configAsProperties();
	}

	private void configAsProperties() {
		ClassLoader context = Thread.currentThread().getContextClassLoader();
		try (InputStream inputStream = context.getResourceAsStream("conf.properties");){
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		return properties;
	}
	
	
}
