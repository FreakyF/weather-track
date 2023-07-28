package org.weathertrack.weather.provider.openmeteo.model.weather;

import org.weathertrack.logging.Logger;
import org.weathertrack.logging.factory.LoggerFactory;
import org.weathertrack.weather.resource.WeatherDisplayResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WeatherInterpretationResource {
	private static Logger logger;
	private static final String PROPERTIES_FILE = "weather_descriptions.properties";
	private static Properties properties;

	private WeatherInterpretationResource(LoggerFactory loggerFactory) {
		logger = loggerFactory.create(WeatherInterpretationResource.class);
	}

	static {
		try (InputStream inputStream = WeatherDisplayResource.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("Failed to load the {}", PROPERTIES_FILE, e);
		}
	}

	public static String interpretWeatherCode(int code) {
		String codeString = String.valueOf(code);
		return properties.getProperty(codeString, "Unknown weather code");
	}
}
