package org.weathertrack.api.service.resource;

import org.weathertrack.api.service.forecast.ForecastApiModule;
import org.weathertrack.api.service.forecast.openmeteo.exception.ForecastIntepreterMessage;
import org.weathertrack.api.service.forecast.openmeteo.exception.NotInitializedException;
import org.weathertrack.logging.Logger;
import org.weathertrack.logging.factory.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApiUriDataResource {
	private static final String PROPERTIES_FILE = "api/service/resource/config.properties";
	private static Logger<ApiUriDataResource> logger;
	private static boolean initialized;

	public static void initialize(LoggerFactory loggerFactory) {
		logger = loggerFactory.create(ApiUriDataResource.class);
		initialized = true;
	}

	public static Map<String, String> getApiUriDataFromProperties(String apiSchemePropertyName,
	                                                              String apiHostPropertyName,
	                                                              String apiPathPropertyName) {
		if (!initialized) {
			throw new NotInitializedException(ForecastIntepreterMessage.FORECAST_DESCRIPTIONS_NOT_INITIALIZED);
		}
		Map<String, String> apiUriData = new HashMap<>();
		try (InputStream inputStream = ForecastApiModule.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			var properties = new Properties();
			properties.load(inputStream);
			apiUriData.put(apiSchemePropertyName, properties.getProperty(apiSchemePropertyName));
			apiUriData.put(apiHostPropertyName, properties.getProperty(apiHostPropertyName));
			apiUriData.put(apiPathPropertyName, properties.getProperty(apiPathPropertyName));
		} catch (IOException e) {
			logger.error("NOT IMPLEMENTED"); // TODO: Improve exception handling. Throw appropiate exception. Handle how user should be notified.
		}
		return apiUriData;
	}

	private ApiUriDataResource() {

	}
}
