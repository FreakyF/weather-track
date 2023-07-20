package org.weathertrack.view.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.weathertrack.model.WeatherData;
import org.weathertrack.service.input.CommandLineUserInputService;
import org.weathertrack.util.logger.LoggerInterface;
import org.weathertrack.view.util.LogMessages;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CommandLineUserInterfaceTests {
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private CommandLineUserInterface commandLineUserInterface;
	private LoggerInterface<CommandLineUserInterface> loggerCLI;
	private static final WeatherData mockWeatherData = new WeatherData(
			"Sunny",
			25.0,
			30,
			10,
			15.0,
			70,
			1015
	);

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() {
		System.setOut(new PrintStream(outputStream));
		CommandLineUserInputService userInputService = mock(CommandLineUserInputService.class);
		loggerCLI = mock(LoggerInterface.class);
		commandLineUserInterface = new CommandLineUserInterface(userInputService, loggerCLI);
	}

	@Test
	void printCitiesWithSameName_shouldPrintCities() {
		// When
		List<String> cities = Arrays.asList("New York", "London", "Paris");

		// Given
		commandLineUserInterface.printCitiesWithSameName(cities);

		// Then
		var expectedOutput = "1. New York\n2. London\n3. Paris";
		assertEquals(expectedOutput, outputStream.toString());
	}

	@Test
	void printCitiesWithSameName_emptyList() {
		// When
		List<String> cities = List.of();

		// Given
		commandLineUserInterface.printCitiesWithSameName(cities);

		// Then
		verify(loggerCLI).warn(LogMessages.CITIES_WITH_SAME_NAME_IS_EMPTY);
	}

	@Test
	void printCitiesWithSameName_singleCity() {
		// When
		List<String> cities = List.of("New York");

		// Given
		commandLineUserInterface.printCitiesWithSameName(cities);

		// Then
		var expectedOutput = "1. New York";
		assertEquals(expectedOutput, outputStream.toString());
	}

	@Test
	void printWeather_shouldPrintWeather() {
		// Given
		commandLineUserInterface.printWeather(mockWeatherData);

		// Then
		var expectedOutput =
				"""
						Weather Condition: Sunny\r
						Temperature: 25.0\r
						Cloudiness: 30%\r
						Rain Chance: 10%\r
						Wind Speed: 15.0\r
						Humidity: 70%\r
						Pressure: 1015 hPa\r
						""";
		assertEquals(expectedOutput, outputStream.toString());
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"Weather Condition: Sunny",
			"Temperature: 25.0",
			"Cloudiness: 30%",
			"Rain Chance: 10%",
			"Wind Speed: 15.0",
			"Humidity: 70%",
			"Pressure: 1015 hPa"
	})
	void printWeather_shouldPrintWeatherInformation(String expectedOutput) {
		// Given
		commandLineUserInterface.printWeather(mockWeatherData);

		// Then
		var output = outputStream.toString();
		assertTrue(output.contains(expectedOutput));
	}
}
