package org.weathertrack.api.service.forecast.openmeteo.resource;

import org.weathertrack.api.service.exception.NotInitializedException;
import org.weathertrack.api.service.forecast.openmeteo.exception.ForecastIntepreterMessage;
import org.weathertrack.logging.Logger;
import org.weathertrack.logging.factory.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ForecastInterpreter {
	private static final String PROPERTIES_FILE = "api/service/forecast/openmeteo/resource/forecast_descriptions.properties";
	private static Properties properties;
	private static String defaultWeatherInterpretation;
	private static Logger<ForecastInterpreter> logger;
	private static boolean initialized;

	private ForecastInterpreter() {
	}

	public static void initialize(LoggerFactory loggerFactory) {
		logger = loggerFactory.create(ForecastInterpreter.class);
		loadProperties();
		defaultWeatherInterpretation = properties.getProperty("UNKNOWN_WEATHER_CODE");
		initialized = true;
	}

	private static void loadProperties() {
		try (InputStream inputStream = ForecastInterpreter.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("Error loading properties: " + e.getMessage());
		}
	}

	public static String interpretWeatherCode(int code) {
		if (!initialized) {
			throw new NotInitializedException(ForecastIntepreterMessage.FORECAST_DESCRIPTIONS_NOT_INITIALIZED);
		}
		return properties.getProperty(String.valueOf(code), defaultWeatherInterpretation);
	}
}
