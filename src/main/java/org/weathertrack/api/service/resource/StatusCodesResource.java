package org.weathertrack.api.service.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StatusCodesResource {
	private static final String PROPERTIES_FILE = "api/service/resource/status_codes.properties";
	private static Properties properties;

	static {
		try (InputStream inputStream = StatusCodesResource.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String STATUS_CODE_429 = properties.getProperty("STATUS_CODE_429");
	public static final String STATUS_CODE_500 = properties.getProperty("STATUS_CODE_500");
	public static final String STATUS_CODE_503 = properties.getProperty("STATUS_CODE_503");
	public static final String STATUS_CODE_504 = properties.getProperty("STATUS_CODE_504");

	private StatusCodesResource() {
	}
}
