package org.weathertrack.input.service.userio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.weathertrack.input.resource.InputLogMessage;
import org.weathertrack.logging.Logger;
import org.weathertrack.logging.factory.LoggerFactory;
import org.weathertrack.weather.model.WeatherData;
import org.weathertrack.weather.provider.openmeteo.model.city.CityData;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandLineUserIOServiceTests {
	private static final String EXPECTED_USER_MESSAGE = "User message";
	private static final String EXPECTED_PROMPT_MESSAGE = "Prompt message";
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private UserIOService commandLineUserInterface;
	private Logger<CommandLineUserIOService> logger;
	private Scanner mockScanner;
	private static final WeatherData mockWeatherData = new WeatherData(
			"Sunny",
			25.0,
			30,
			10,
			15.0,
			70,
			1015
	);

	@BeforeEach
	void setUp() {
		System.setOut(new PrintStream(outputStream));
		var loggerFactory = mock(LoggerFactory.class);
		logger = mock(Logger.class);
		when(loggerFactory.create(CommandLineUserIOService.class)).thenReturn(logger);
		mockScanner = mock(Scanner.class);
		commandLineUserInterface = new CommandLineUserIOService(loggerFactory, mockScanner);
	}

	@Test
	void getUserInput_ShouldReturnUserInput() {
		// When
		when(mockScanner.nextLine()).thenReturn(EXPECTED_USER_MESSAGE);

		// Given
		var result = commandLineUserInterface.getUserInput(EXPECTED_PROMPT_MESSAGE);

		// Then
		assertEquals(EXPECTED_USER_MESSAGE, result);
	}

	@Test
	void getUserInput_ShouldPromptMessage() {
		// When
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));

		when(mockScanner.nextLine()).thenReturn(EXPECTED_USER_MESSAGE);

		// Given
		commandLineUserInterface.getUserInput(EXPECTED_PROMPT_MESSAGE);

		// Then
		assertEquals(EXPECTED_PROMPT_MESSAGE, outputStreamCaptor.toString().trim());
	}

	@Test
	void printCityDataWithSameCityName_shouldPrintCityData() {
		// When
		long[] postCodes = {43438, 21312, 55122};
		List<CityData> cityData = List.of(
				new CityData(0,
						"Kielce",
						12.2,
						53.3,
						4,
						"FeatueCode",
						"PL",
						7,
						8,
						9,
						0,
						"Warsaw",
						1200L,
						postCodes,
						13,
						"Poland",
						"Swiętokrzyskie",
						"Kielce",
						"Something",
						"Something4"),
				new CityData(
						0,
						"Brenna",
						12.1,
						12.3,
						5,
						"FeatueCode",
						"PL",
						10,
						12,
						11,
						4,
						"Warsaw",
						11200L,
						postCodes,
						15,
						"Poland",
						"Śląskie",
						"Brenna",
						"Something",
						"Something4"
				)
		);

		// Given
		commandLineUserInterface.printCityDataWithSameCityName(cityData);

		// Then
		var expectedOutput = """
				1. Kielce, Swiętokrzyskie, Poland
				2. Brenna, Śląskie, Poland
				""";
		assertEquals(expectedOutput, outputStream.toString());
	}

	@Test
	void printCityDataWithSameCityName_WhenCityData_isEmpty_ShouldLogWarn() {
		// When
		List<CityData> cityData = List.of();

		// Given
		commandLineUserInterface.printCityDataWithSameCityName(cityData);

		// Then
		verify(logger).warn(InputLogMessage.CITY_DATA_WITH_SAME_CITY_NAME_IS_EMPTY);
	}

	@Test
	void printCityDataWithSameCityName_singleCity() {
		// When
		long[] postCodes = {43438, 21312, 55122};
		List<CityData> cityData = List.of(
				new CityData(0,
						"Kielce",
						12.2,
						53.3,
						4,
						"IDK",
						"PL",
						7,
						8,
						9,
						0,
						"Warsaw",
						1200L,
						postCodes,
						13,
						"Poland",
						"Swiętokrzyskie",
						"Kielce",
						"Something",
						"Idk")
		);

		// Given
		commandLineUserInterface.printCityDataWithSameCityName(cityData);

		// Then
		var expectedOutput = """
				1. Kielce, Swiętokrzyskie, Poland
				""";
		assertEquals(expectedOutput, outputStream.toString());
	}

	@Test
	void printWeather_shouldPrintWeather() {
		// Given
		commandLineUserInterface.printWeather(mockWeatherData);

		// Then
		var newLine = System.lineSeparator();
		var expectedOutput = "Weather Condition: Sunny" + newLine +
				"Temperature: 25.0" + newLine +
				"Cloudiness: 30%" + newLine +
				"Rain Chance: 10%" + newLine +
				"Wind Speed: 15.0" + newLine +
				"Humidity: 70%" + newLine +
				"Pressure: 1015 hPa";
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
