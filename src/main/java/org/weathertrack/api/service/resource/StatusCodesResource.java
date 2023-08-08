package org.weathertrack.api.service.resource;

import org.weathertrack.input.validation.resource.ValidationMessageResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StatusCodesResource {
	private static final String PROPERTIES_FILE = "status_codes.properties";
	private static Properties properties;

	static {
		try (InputStream inputStream = ValidationMessageResource.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String STATUS_CODE_400 = properties.getProperty("STATUS_CODE_400");
	public static final String STATUS_CODE_404 = properties.getProperty("STATUS_CODE_404");
	public static final String STATUS_CODE_500 = properties.getProperty("STATUS_CODE_500");
	public static final String STATUS_CODE_503 = properties.getProperty("STATUS_CODE_503");
	public static final String STATUS_CODE_504 = properties.getProperty("STATUS_CODE_504");

	private StatusCodesResource() {
	}
}
