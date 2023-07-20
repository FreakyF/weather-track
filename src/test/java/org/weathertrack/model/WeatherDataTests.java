package org.weathertrack.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.weathertrack.view.util.LogMessages;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeatherDataTests {

	private WeatherData weatherData;

	private static Stream<Arguments> WeatherCondition_ShouldThrowError_withAppropriateMessage() {
		return Stream.of(
				Arguments.of(null, LogMessages.WEATHER_CONDITION_IS_NULL),
				Arguments.of("", LogMessages.WEATHER_CONDITION_IS_EMPTY),
				Arguments.of(" ", LogMessages.WEATHER_CONDITION_IS_EMPTY)
		);
	}

	@ParameterizedTest
	@MethodSource
	void WeatherCondition_ShouldThrowError_withAppropriateMessage(String weatherConditionValue, String exceptionMessage) {
		// Given
		var throwedException = assertThrows(IllegalStateException.class, () -> weatherData = new WeatherData(
				weatherConditionValue,
				15.2,
				10,
				20,
				30.2,
				40,
				1015
		));

		// Then
		assertEquals(exceptionMessage, throwedException.getMessage());
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
				1015
		);

		// Given
		var result = weatherData.weatherCondition();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void TemperatureCelsius_ShouldThrowError_WhenBelowAbsoluteZero() {
		// Given
		var throwedException = assertThrows(IllegalArgumentException.class, () -> weatherData = new WeatherData(
				"Sunny",
				-318.5,
				10,
				20,
				30.2,
				40,
				1015
		));

		// Then
		assertEquals(LogMessages.TEMPERATURE_IS_BELOW_ABSOLUTE_ZERO, throwedException.getMessage());
	}

	@Test
	void TemperatureCelsius_whenTemperatureCelsiusIsGood_ShouldReturnTemperatureCelsius() {
		// When
		final var expectedOutput = 20.2;
		weatherData = new WeatherData(
				"Sunny",
				20.2,
				10,
				20,
				30.2,
				40,
				1015
		);

		// Given
		var result = weatherData.temperatureCelsius();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void TemperatureCelsius_whenIsNegative_ShouldReturnTemperatureCelsius() {
		// When
		final var expectedOutput = -20.2;
		weatherData = new WeatherData(
				"Sunny",
				-20.2,
				10,
				20,
				30.2,
				40,
				1015
		);

		// Given
		var result = weatherData.temperatureCelsius();

		// Then
		assertEquals(expectedOutput, result);
	}

	@Test
	void TemperatureCelsius_whenIsZero_ShouldReturnTemperatureCelsius() {
		// When
		final var expectedOutput = 0;
		weatherData = new WeatherData(
				"Sunny",
				0,
				10,
				20,
				30.2,
				40,
				1015
		);

		// Given
		var result = weatherData.temperatureCelsius();

		// Then
		assertEquals(expectedOutput, result);
	}

	private static Stream<Arguments> Cloudiness_ShouldThrowError_withAppropriateMessage() {
		return Stream.of(
				Arguments.of(101, LogMessages.CLOUDINESS_IS_ABOVE_100),
				Arguments.of(-1, LogMessages.CLOUDINESS_IS_BELOW_0)
		);
	}

	@ParameterizedTest
	@MethodSource
	void Cloudiness_ShouldThrowError_withAppropriateMessage(int cloudinessValue, String exceptionMessage) {
		// Given
		var throwedException = assertThrows(IllegalArgumentException.class, () -> weatherData = new WeatherData(
				"Sunny",
				15.2,
				cloudinessValue,
				20,
				30.2,
				40,
				1015
		));

		// Then
		assertEquals(exceptionMessage, throwedException.getMessage());
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
				1015
		);

		// Given
		var result = weatherData.cloudiness();

		// Then
		assertEquals(expectedOutput, result);
	}

	private static Stream<Arguments> RainChance_ShouldThrowError_WithAppropriateMessage() {
		return Stream.of(
				Arguments.of(101, LogMessages.RAIN_CHANCE_IS_ABOVE_100),
				Arguments.of(-1, LogMessages.RAIN_CHANCE_IS_BELOW_0)
		);
	}

	@ParameterizedTest
	@MethodSource
	void RainChance_ShouldThrowError_WithAppropriateMessage(int rainChanceValue, String exceptionMessage) {
		// Given
		var throwedException = assertThrows(IllegalArgumentException.class, () -> weatherData = new WeatherData(
				"Sunny",
				15.2,
				10,
				rainChanceValue,
				30.2,
				40,
				1015
		));

		// Then
		assertEquals(exceptionMessage, throwedException.getMessage());
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
				1015
		);

		// Given
		var result = weatherData.rainChance();

		// Then
		assertEquals(expectedOutput, result);
	}

	private static Stream<Arguments> WindSpeed_ShouldThrowError_WithAppropriateMessage() {
		return Stream.of(
				Arguments.of(-32.5, LogMessages.WIND_SPEED_IS_BELOW_0)
		);
	}

	@ParameterizedTest
	@MethodSource
	void WindSpeed_ShouldThrowError_WithAppropriateMessage(double windSpeedValue, String exceptionMessage) {
		// Given
		var throwedException = assertThrows(IllegalArgumentException.class, () -> weatherData = new WeatherData(
				"Sunny",
				15.2,
				10,
				10,
				windSpeedValue,
				40,
				1015
		));

		// Then
		assertEquals(exceptionMessage, throwedException.getMessage());
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
				1015
		);

		// Given
		var result = weatherData.rainChance();

		// Then
		assertEquals(expectedOutput, result);
	}

	private static Stream<Arguments> Humidity_ShouldThrowError_WithAppropriateMessag() {
		return Stream.of(
				Arguments.of(101, LogMessages.HUMIDITY_IS_ABOVE_100),
				Arguments.of(-1, LogMessages.HUMIDITY_IS_BELOW_0)
		);
	}

	@ParameterizedTest
	@MethodSource
	void Humidity_ShouldThrowError_WithAppropriateMessag(int humidityValue, String exceptionMessage) {
		// Given
		var throwedException = assertThrows(IllegalArgumentException.class, () -> weatherData = new WeatherData(
				"Sunny",
				15.2,
				10,
				10,
				32.5,
				humidityValue,
				1015
		));

		// Then
		assertEquals(exceptionMessage, throwedException.getMessage());
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
				1015
		);

		// Given
		var result = weatherData.humidity();

		// Then
		assertEquals(expectedOutput, result);
	}

	private static Stream<Arguments> Pressure_ShouldThrowError_WithAppropriateMessage() {
		return Stream.of(
				Arguments.of(-32, LogMessages.PRESSURE_IS_BELOW_0)
		);
	}

	@ParameterizedTest
	@MethodSource
	void Pressure_ShouldThrowError_WithAppropriateMessage(int pressureValue, String exceptionMessage) {
		// Given
		var throwedException = assertThrows(IllegalArgumentException.class, () -> weatherData = new WeatherData(
				"Sunny",
				15.2,
				10,
				10,
				32.5,
				40,
				pressureValue
		));

		// Then
		assertEquals(exceptionMessage, throwedException.getMessage());
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
				1015
		);

		// Given
		var result = weatherData.pressure();

		// Then
		assertEquals(expectedOutput, result);
	}
}
