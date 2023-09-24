package org.weathertrack.api.service.forecast.openmeteo.resource;

import org.weathertrack.api.service.exception.NotInitializedException;
import org.weathertrack.api.service.forecast.openmeteo.exception.OpenMeteoForecastApiServiceResourceMessage;
import org.weathertrack.logging.Logger;
import org.weathertrack.logging.factory.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OpenMeteoForecastApiServiceResource {
	private static final String PROPERTIES_FILE = "api/service/forecast/OpenMeteoForecastApiService.properties";
	private static Logger<OpenMeteoForecastApiServiceResource> logger;
	private static boolean initialized;
	public static final String COULD_NOT_GET_FORECAST_FOR_COORDINATES;

	static {
		COULD_NOT_GET_FORECAST_FOR_COORDINATES = loadProperties();
	}

	public static void initialize(LoggerFactory loggerFactory) {
		logger = loggerFactory.create(OpenMeteoForecastApiServiceResource.class);
		loadProperties();
		initialized = true;
	}

	private static String loadProperties() {
		if (!initialized) {
			throw new NotInitializedException(OpenMeteoForecastApiServiceResourceMessage.FORECAST_API_SERVICE_LOCALIZATION_NOT_INITIALIZED);
		}
		try (InputStream inputStream = OpenMeteoForecastApiServiceResource.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties.getProperty("COULD_NOT_GET_FORECAST_FOR_COORDINATES");
		} catch (IOException e) {
			logger.error("Error loading properties: " + e.getMessage());
			return null;
		}
	}

	private OpenMeteoForecastApiServiceResource() {
	}
}
