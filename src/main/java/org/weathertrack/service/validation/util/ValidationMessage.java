package org.weathertrack.service.validation.util;

import org.weathertrack.view.util.WeatherUIResources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ValidationMessage {
	private static final String PROPERTIES_FILE = "ValidationMessage.properties";
	private static Properties properties;

	static {
		try (InputStream inputStream = WeatherUIResources.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String PLEASE_TRY_AGAIN
			= properties.getProperty("PLEASE_TRY_AGAIN");
	public static final String CITY_INPUT_EMPTY
			= properties.getProperty("CITY_INPUT_EMPTY");
	public static final String CITY_INPUT_TOO_LONG
			= properties.getProperty("CITY_INPUT_TOO_LONG");
	public static final String CITY_INPUT_CONTAINS_NUMBERS
			= properties.getProperty("CITY_INPUT_CONTAINS_NUMBERS");
	public static final String CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS
			= properties.getProperty("CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS");

	private ValidationMessage() {
	}
}
