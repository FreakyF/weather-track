package org.weathertrack.api.service.forecast.openmeteo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.weathertrack.TestData;
import org.weathertrack.api.service.forecast.openmeteo.model.WeatherReport;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;

import java.util.ArrayList;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class OpenMeteoForecastApiServiceTests {
	private static WeatherReport MOCKED_WEATHER_REPORT;
	private AutoCloseable closeable;
	@InjectMocks
	private OpenMeteoForecastApiService sut;

	@BeforeEach
	void beforeEach() {
		closeable = MockitoAnnotations.openMocks(this);
		MOCKED_WEATHER_REPORT = TestData.Provider.createWeatherReport();
		sut = new OpenMeteoForecastApiService();
	}

	@AfterEach
	void afterEach() throws Exception {
		closeable.close();
	}

	@Test
	void fetchForecastForCoordinates_WhenGeocodingCityDataIsNull_ShouldThrowNullPointerException() {
		// When
		var mockGeocodingCityData = new GeocodingCityData("Kielce", "Świętokrzyskie", "Poland", 21, 37);

		// Given
		var result = sut.fetchForecastForCoordinates(mockGeocodingCityData);

		// Then
	}

	// TODO: Add what exception should be thrown.
	@Test
	void fetchForecastForCoordinates_WhenUriSyntaxIsInvalid_ShouldThrowException() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchForecastForCoordinates_WhenGeocodingCityDataAndUriIsValid_ShouldReturnSuccessfulResponseData() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchForecastForCoordinates_WhenWeatherReportIsNull_ShouldThrowNullPointerException() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchForecastForCoordinates_WhenWeatherReportIsEmpty_ShouldThrowException() {
		// When

		// Given

		// Then
	}

	private static Stream<Arguments> fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldReturnResponseData() {
		return Stream.of(
				Arguments.of()
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldReturnResponseData(
			int statusCodeValue, boolean success, String responseMessage, ArrayList<GeocodingCityData> expectedCityDataResponse) {
		// When

		// Given

		// Then
	}
	// TODO: Add what exception should be thrown.

	private static Stream<Arguments> fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldThrowException() {
		return Stream.of(
				Arguments.of()
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldThrowException(
			int statusCodeValue, boolean success, String responseMessage, ArrayList<GeocodingCityData> expectedCityDataResponse) {
		// When

		// Given

		// Then
	}
}
