package org.weathertrack.api.service.resource;

import org.weathertrack.api.service.forecast.ForecastApiModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApiUriDataResource {
	private static final String PROPERTIES_FILE = "api/config.properties";

	public static Map<String, String> getApiUriDataFromProperties(String apiSchemePropertyName, String apiHostPropertyName, String apiPathPropertyName) {
		Map<String, String> apiUriData = new HashMap<>();
		try (InputStream inputStream = ForecastApiModule.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			var properties = new Properties();
			properties.load(inputStream);
			apiUriData.put(apiSchemePropertyName, properties.getProperty(apiSchemePropertyName));
			apiUriData.put(apiHostPropertyName, properties.getProperty(apiHostPropertyName));
			apiUriData.put(apiPathPropertyName, properties.getProperty(apiPathPropertyName));
		} catch (IOException e) {
			e.printStackTrace(); // TODO: Improve exception handling. Throw appropiate exception. Handle how user should be notified.
		}
		return apiUriData;
	}

	private ApiUriDataResource() {

	}
}
