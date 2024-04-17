package org.weathertrack.input.validation.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ValidationMessageResource {
	private static final String PROPERTIES_FILE = "input/validation/resource/validation_message.properties";
	private static Properties properties;

	static {
		try (InputStream inputStream = ValidationMessageResource.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String PLEASE_TRY_AGAIN
			= properties.getProperty("PLEASE_TRY_AGAIN");
	public static final String CITY_INPUT_BLANK
			= properties.getProperty("CITY_INPUT_BLANK") + PLEASE_TRY_AGAIN;
	public static final String CITY_INPUT_TOO_LONG
			= properties.getProperty("CITY_INPUT_TOO_LONG") + PLEASE_TRY_AGAIN;
	public static final String CITY_INPUT_CONTAINS_NUMBERS
			= properties.getProperty("CITY_INPUT_CONTAINS_NUMBERS") + PLEASE_TRY_AGAIN;
	public static final String CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS
			= properties.getProperty("CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS") + PLEASE_TRY_AGAIN;
	public static final String MENU_ENTRY_IS_ZERO
			= properties.getProperty("MENU_ENTRY_IS_ZERO") + PLEASE_TRY_AGAIN;

	private ValidationMessageResource() {
	}
}
