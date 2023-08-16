package org.weathertrack.api.service.forecast.openmeteo.resource;

import org.weathertrack.input.service.userio.resource.WeatherDisplayResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ForecastInterpreter {
	private static final String PROPERTIES_FILE = "forecast_descriptions.properties";
	private static Properties properties;

	private ForecastInterpreter() {
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
		return properties.getProperty(codeString, "Unknown weather code");
	}
}
