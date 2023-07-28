package org.weathertrack.api.geocoding.openmeteo.model.weather;

import org.weathertrack.api.weather.resource.WeatherDisplayResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WeatherInterpretationResource {
	private static final String PROPERTIES_FILE = "weather_descriptions.properties";
	private static Properties properties;

	private WeatherInterpretationResource() {
	}

	static {
		try (InputStream inputStream = WeatherDisplayResource.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String interpretWeatherCode(int code) {
		String codeString = String.valueOf(code);
		return properties.getProperty(codeString, "Unknown api code");
	}
}
