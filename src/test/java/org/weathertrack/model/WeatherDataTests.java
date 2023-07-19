package org.weathertrack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.weathertrack.util.logger.LoggerInterface;
import org.weathertrack.view.util.LogMessages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WeatherDataTests {
	private LoggerInterface<WeatherData> logger;

	private WeatherData weatherData;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() {
		logger = mock(LoggerInterface.class);
	}

	@Test
	void WeatherCondition_whenIsNull_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				null,
				15.2,
				10,
				20,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		weatherData.getWeatherCondition();

		// Then
		verify(logger).error(LogMessages.WEATHER_CONDITION_IS_NULL);
	}

	@Test
	void WeatherCondition_whenIsEmptyString_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				"",
				15.2,
				10,
				20,
				30.2,
				40,
				1015,
				logger
		);
		// Given
		weatherData.getWeatherCondition();

		// Then
		verify(logger).error(LogMessages.WEATHER_CONDITION_IS_EMPTY);
	}

	@Test
	void WeatherCondition_whenInputIsGood_ShouldReturnGoodWeatherCondition() {
		// When
		final var expectedOutput = "Sunny";
		weatherData = new WeatherData(
				"Sunny",
				15.2,
				10,
				20,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		var result = weatherData.getWeatherCondition();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void Temperature_whenTemperatureIsGood_ShouldReturnTemperature() {
		// When
		final var expectedOutput = 20.2;
		weatherData = new WeatherData(
				"Sunny",
				20.2,
				10,
				20,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		var result = weatherData.getTemperature();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void Temperature_whenIsNegative_ShouldReturnTemperature() {
		// When
		final var expectedOutput = -20.2;
		weatherData = new WeatherData(
				"Sunny",
				-20.2,
				10,
				20,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		var result = weatherData.getTemperature();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void Temperature_whenIsZero_ShouldReturnTemperature() {
		// When
		final var expectedOutput = 0;
		weatherData = new WeatherData(
				"Sunny",
				0,
				10,
				20,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		var result = weatherData.getTemperature();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void Cloudiness_whenIsOverOneHundred_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				"Sunny",
				0,
				101,
				20,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		weatherData.getCloudiness();

		// Then
		verify(logger).error(LogMessages.CLOUDINESS_IS_ABOVE_100);
	}

	@Test
	void Cloudiness_whenIsBelowZero_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				"Sunny",
				0,
				-1,
				20,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		weatherData.getCloudiness();

		// Then
		verify(logger).error(LogMessages.CLOUDINESS_IS_BELOW_0);
	}

	@Test
	void Cloudiness_whenIsNormal_ShouldReturnCloudiness() {
		// When
		final var expectedOutput = 30;
		weatherData = new WeatherData(
				"Sunny",
				-20.2,
				30,
				20,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		var result = weatherData.getCloudiness();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void RainChance_whenIsAboveHundred_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				"Sunny",
				0,
				10,
				101,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		weatherData.getRainChance();

		// Then
		verify(logger).error(LogMessages.RAIN_CHANCE_IS_ABOVE_100);
	}

	@Test
	void RainChance_whenIsBelowZero_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				"Sunny",
				0,
				10,
				-1,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		weatherData.getRainChance();

		// Then
		verify(logger).error(LogMessages.RAIN_CHANCE_IS_BELOW_0);
	}

	@Test
	void RainChance_whenIsNormal_ShouldReturnRainChance() {
		// When
		final var expectedOutput = 30;
		weatherData = new WeatherData(
				"Sunny",
				-20.2,
				10,
				30,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		var result = weatherData.getRainChance();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void WindSpeed_whenIsBelowZero_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				"Sunny",
				0,
				10,
				10,
				-30,
				40,
				1015,
				logger
		);

		// Given
		weatherData.getWindSpeed();

		// Then
		verify(logger).error(LogMessages.WIND_SPEED_IS_BELOW_0);
	}

	@Test
	void WindSpeed_whenIsNormal_ShouldReturnWindSpeed() {
		// When
		final var expectedOutput = 30;
		weatherData = new WeatherData(
				"Sunny",
				20.2,
				10,
				30,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		var result = weatherData.getRainChance();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void Humidity_whenIsAboveHundred_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				"Sunny",
				0,
				10,
				10,
				30,
				101,
				1015,
				logger
		);

		// Given
		weatherData.getHumidity();

		// Then
		verify(logger).error(LogMessages.HUMIDITY_IS_ABOVE_100);
	}

	@Test
	void Humidity_whenIsBelowZero_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				"Sunny",
				0,
				10,
				10,
				30,
				-1,
				1015,
				logger
		);

		// Given
		weatherData.getHumidity();

		// Then
		verify(logger).error(LogMessages.HUMIDITY_IS_BELOW_0);
	}

	@Test
	void Humidity_whenIsNormal_ShouldReturnHumidity() {
		// When
		final var expectedOutput = 40;
		weatherData = new WeatherData(
				"Sunny",
				20.2,
				10,
				30,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		var result = weatherData.getHumidity();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void Pressure_whenIsBelowZero_ShouldLogError() {
		// When
		weatherData = new WeatherData(
				"Sunny",
				0,
				10,
				10,
				30,
				10,
				-1,
				logger
		);

		// Given
		weatherData.getPressure();

		// Then
		verify(logger).error(LogMessages.PRESSURE_IS_BELOW_0);
	}

	@Test
	void Pressure_whenIsNormal_ShouldReturnPressure() {
		// When
		final var expectedOutput = 1015;
		weatherData = new WeatherData(
				"Sunny",
				20.2,
				10,
				30,
				30.2,
				40,
				1015,
				logger
		);

		// Given
		var result = weatherData.getPressure();

		// Then
		assertEquals(expectedOutput, result);
	}
}
