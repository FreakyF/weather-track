package org.weathertrack.api.resource;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.weathertrack.input.service.userio.resource.WeatherDisplayResource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherDisplayResourceTests {
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
			case "WEATHER_CONDITION" -> WeatherDisplayResource.WEATHER_CONDITION;
			case "TEMPERATURE" -> WeatherDisplayResource.TEMPERATURE;
			case "CLOUDINESS" -> WeatherDisplayResource.CLOUDINESS;
			case "RAIN_CHANCE" -> WeatherDisplayResource.RAIN_CHANCE;
			case "WIND_SPEED" -> WeatherDisplayResource.WIND_SPEED;
			case "HUMIDITY" -> WeatherDisplayResource.HUMIDITY;
			case "PRESSURE" -> WeatherDisplayResource.PRESSURE;
			default -> throw new IllegalArgumentException("Invalid propertyName: " + propertyName);
		};
	}
}
