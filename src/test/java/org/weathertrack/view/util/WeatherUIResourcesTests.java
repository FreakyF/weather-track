package org.weathertrack.view.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherUIResourcesTests {
	@ParameterizedTest
	@CsvSource({
			"Weather Condition: %s, WEATHER_CONDITION",
			"Temperature: %.1f, TEMPERATURE",
			"Cloudiness: %d%%, CLOUDINESS",
			"Rain Chance: %d%%, RAIN_CHANCE",
			"Wind Speed: %.1f, WIND_SPEED",
			"Humidity: %d%%, HUMIDITY",
			"Pressure: %d hPa, PRESSURE"
	})
	void staticFields_shouldGetProperties(String expectedOutput, String propertyName) {
		// Given
		String actual = getProperty(propertyName);

		// Then
		assertEquals(expectedOutput, actual);
	}

	private String getProperty(String propertyName) {
		return switch (propertyName) {
			case "WEATHER_CONDITION" -> WeatherUIResources.WEATHER_CONDITION;
			case "TEMPERATURE" -> WeatherUIResources.TEMPERATURE;
			case "CLOUDINESS" -> WeatherUIResources.CLOUDINESS;
			case "RAIN_CHANCE" -> WeatherUIResources.RAIN_CHANCE;
			case "WIND_SPEED" -> WeatherUIResources.WIND_SPEED;
			case "HUMIDITY" -> WeatherUIResources.HUMIDITY;
			case "PRESSURE" -> WeatherUIResources.PRESSURE;
			default -> throw new IllegalArgumentException("Invalid propertyName: " + propertyName);
		};
	}
}
