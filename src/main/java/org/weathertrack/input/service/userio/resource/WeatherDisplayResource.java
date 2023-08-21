package org.weathertrack.input.service.userio.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WeatherDisplayResource {
	private static final String PROPERTIES_FILE = "input/service/userio/resource/forecast_display.properties";
	private static Properties properties;

	static {
		try (InputStream inputStream = WeatherDisplayResource.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String WEATHER_CONDITION = properties.getProperty("WEATHER_CONDITION");
	public static final String TEMPERATURE = properties.getProperty("TEMPERATURE");
	public static final String CLOUDINESS = properties.getProperty("CLOUDINESS");
	public static final String RAIN_CHANCE = properties.getProperty("RAIN_CHANCE");
	public static final String WIND_SPEED = properties.getProperty("WIND_SPEED");
	public static final String HUMIDITY = properties.getProperty("HUMIDITY");
	public static final String PRESSURE = properties.getProperty("PRESSURE");

	private WeatherDisplayResource() {
	}
}
