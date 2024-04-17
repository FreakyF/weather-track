package org.weathertrack.input.service.userio;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.weathertrack.TestData;
import org.weathertrack.api.service.forecast.model.ForecastData;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.input.resource.InputLogMessage;
import org.weathertrack.logging.Logger;
import org.weathertrack.logging.factory.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandLineUserIOServiceTests {
	private static ForecastData MOCKED_FORECAST_DATA;
	private static GeocodingCityData MOCKED_GEOCODING_CITY_DATA;
	private static final String EXPECTED_USER_MESSAGE = "User message";
	private static final String EXPECTED_PROMPT_MESSAGE = "Prompt message";
	private ByteArrayOutputStream outputStreamCaptor;
	@Mock
	private LoggerFactory loggerFactory;
	@Mock
	private Logger<CommandLineUserIOService> logger;
	@Mock
	private Scanner mockScanner;
	private AutoCloseable closeable;
	@InjectMocks
	private CommandLineUserIOService sut;

	@BeforeEach
	void beforeEach() {
		closeable = MockitoAnnotations.openMocks(this);

		MOCKED_FORECAST_DATA = TestData.Provider.createForecastData();
		MOCKED_GEOCODING_CITY_DATA = TestData.Provider.createGeocodingCityData();

		when(loggerFactory.create(CommandLineUserIOService.class)).thenReturn(logger);

		sut = new CommandLineUserIOService(loggerFactory, mockScanner);

		outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@AfterEach
	void afterEach() throws Exception {
		closeable.close();
		System.setOut(System.out);
	}

	@Test
	void getUserInput_ShouldReturnUserInput() {
		// When
		when(mockScanner.nextLine()).thenReturn(EXPECTED_USER_MESSAGE);

		// Given
		var result = sut.getUserInput(EXPECTED_PROMPT_MESSAGE);

		// Then
		assertEquals(EXPECTED_USER_MESSAGE, result);
	}

	@Test
	void getUserInput_ShouldPromptMessage() {
		// When
		when(mockScanner.nextLine()).thenReturn(EXPECTED_USER_MESSAGE);

		// Given
		sut.getUserInput(EXPECTED_PROMPT_MESSAGE);

		// Then
		assertEquals(EXPECTED_PROMPT_MESSAGE, outputStreamCaptor.toString());
	}

	@Test
	void printCityDataWithSameCityName_WhenGeocodingCityDataIsValid_ShouldPrintCitiesWithSameName() {
		// When
		List<GeocodingCityData> mockedList = new ArrayList<>();
		mockedList.add(MOCKED_GEOCODING_CITY_DATA);

		var expectedOutput = """
				1. Kielce, Świętokrzyskie, Poland
				""";

		// Given
		sut.printCitiesWithSameName(mockedList);

		// Then
		assertEquals(expectedOutput, outputStreamCaptor.toString());
	}

	@Test
	void printCityDataWithSameCityName_WhenCityData_isEmpty_ShouldLogWarn() {
		// When
		List<GeocodingCityData> GeocodingCityDataList = List.of();

		// Given
		sut.printCitiesWithSameName(GeocodingCityDataList);

		// Then
		verify(logger).warn(InputLogMessage.CITIES_WITH_SAME_NAME_IS_EMPTY);
	}

	@Test
	void printCityDataWithSameCityName_WhenSingleCity_ShouldReturnCityData() {
		// When
		List<GeocodingCityData> GeocodingCityDataList = List.of(
				new GeocodingCityData(
						"Kielce",
						"Świętokrzyskie",
						"Poland",
						21,
						37)
		);

		// Given
		sut.printCitiesWithSameName(GeocodingCityDataList);

		// Then
		var expectedOutput = """
				1. Kielce, Świętokrzyskie, Poland
				""";
		assertEquals(expectedOutput, outputStreamCaptor.toString());
	}

	@Test
	void printWeather_shouldPrintWeather() {
		// When
		sut.printWeather(MOCKED_FORECAST_DATA);

		// Then
		var newLine = System.lineSeparator();
		var expectedOutput =
				newLine +
						"Weather forecast for timezone: Europe/Warsaw (UTC offset: 3600 seconds)" + newLine + newLine +
						"Hourly forecast:" + newLine +
						"------------------------------" + newLine +
						"Time: 2023-08-18T21:04:16.056821" + newLine +
						"Temperature: 22.0 °C" + newLine +
						"Weather code: Mainly clear" + newLine +
						"Wind speed: 10.1 Kph" + newLine +
						"Precipitation: 2 mm" + newLine +
						"Humidity: 2 %" + newLine +
						"------------------------------" + newLine + newLine +
						"Daily forecast:" + newLine +
						"------------------------------" + newLine +
						"Date: 2023-08-18" + newLine +
						"Max temperature: 25.5 °C" + newLine +
						"Weather code: Clear sky" + newLine +
						"Max wind speed: 8.3 Kph" + newLine +
						"Precipitation probability: 5 %" + newLine +
						"------------------------------" + newLine;

		assertEquals(expectedOutput.trim(), outputStreamCaptor.toString().trim());
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"Weather forecast for timezone: Europe/Warsaw (UTC offset: 3600 seconds)",
			"\n",
			"Hourly forecast:",
			"------------------------------",
			"Time: 2023-08-18T21:04:16.056821",
			"Temperature: 22.0 °C",
			"Weather code: Mainly clear",
			"Wind speed: 10.1 Kph",
			"Precipitation: 2 mm",
			"Humidity: 2 %",
			"------------------------------",
			"\n",
			"Daily forecast:",
			"------------------------------",
			"Date: 2023-08-18",
			"Max temperature: 25.5 °C",
			"Weather code: Clear sky",
			"Max wind speed: 8.3 Kph",
			"Precipitation probability: 5 %",
			"------------------------------",
			"\n"
	})
	void printWeather_shouldPrintWeatherInformation(String expectedOutput) {
		// When
		sut.printWeather(MOCKED_FORECAST_DATA);

		// Then
		var output = outputStreamCaptor.toString();
		assertTrue(output.contains(expectedOutput));
	}
}
