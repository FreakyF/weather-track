package org.weathertrack.weather.provider.openmeteo.model.weather;

import org.weathertrack.weather.resource.WeatherDisplayResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WeatherInterpretationResource {

	private static final String PROPERTIES_FILE = "weather_descriptions.properties";
	private static Properties properties;

	static {
		try (InputStream inputStream = WeatherDisplayResource.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private WeatherInterpretationResource() {
	}

	private static final Map<Integer, String> weatherDescriptions = new HashMap<>();

	static {
		weatherDescriptions.put(0, properties.getProperty("0"));
		weatherDescriptions.put(1, properties.getProperty("1"));
		weatherDescriptions.put(2, properties.getProperty("2"));
		weatherDescriptions.put(3, properties.getProperty("3"));
		weatherDescriptions.put(45, properties.getProperty("45"));
		weatherDescriptions.put(48, properties.getProperty("48"));
		weatherDescriptions.put(51, properties.getProperty("51"));
		weatherDescriptions.put(53, properties.getProperty("53"));
		weatherDescriptions.put(55, properties.getProperty("55"));
		weatherDescriptions.put(56, properties.getProperty("56"));
		weatherDescriptions.put(57, properties.getProperty("57"));
		weatherDescriptions.put(61, properties.getProperty("61"));
		weatherDescriptions.put(63, properties.getProperty("63"));
		weatherDescriptions.put(65, properties.getProperty("65"));
		weatherDescriptions.put(66, properties.getProperty("66"));
		weatherDescriptions.put(67, properties.getProperty("67"));
		weatherDescriptions.put(71, properties.getProperty("71"));
		weatherDescriptions.put(73, properties.getProperty("73"));
		weatherDescriptions.put(75, properties.getProperty("75"));
		weatherDescriptions.put(77, properties.getProperty("77"));
		weatherDescriptions.put(80, properties.getProperty("80"));
		weatherDescriptions.put(81, properties.getProperty("81"));
		weatherDescriptions.put(82, properties.getProperty("82"));
		weatherDescriptions.put(85, properties.getProperty("85"));
		weatherDescriptions.put(86, properties.getProperty("86"));
		weatherDescriptions.put(95, properties.getProperty("95"));
		weatherDescriptions.put(96, properties.getProperty("96"));
		weatherDescriptions.put(99, properties.getProperty("99"));
	}

	public static String interpretWeatherCode(int code) {
		return weatherDescriptions.getOrDefault(code, "Unknown weather code");
	}
}
