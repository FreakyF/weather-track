package org.weathertrack.api.service.forecast.openmeteo.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ForecastInterpreter {
	private static final String PROPERTIES_FILE = "api/service/forecast/openmeteo/resource/forecast_descriptions.properties";
	private static Properties properties;

	private ForecastInterpreter() {
	}

	static {
		try (InputStream inputStream = ForecastInterpreter.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace(); // TODO: Improve exception catching. Get rid of standalone e.printStackTrace everywhere.
		}
	}

	private static final String DEFAULT_WEATHER_INTERPRETATION = properties.getProperty("UNKNOWN_WEATHER_CODE");

	public static String interpretWeatherCode(int code) {
		return properties.getProperty(String.valueOf(code), DEFAULT_WEATHER_INTERPRETATION);
	}
}
