package org.weathertrack.api.service.forecast.openmeteo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.weathertrack.TestData;
import org.weathertrack.api.service.forecast.openmeteo.model.WeatherReport;

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
	void fetchForecastForCoordinates_WhenGeocodingCityDataIsNull_ShouldThrowException_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchForecastForCoordinates_WhenUriSyntaxIsInvalid_ShouldThrowException_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchForecastForCoordinates_WhenGeocodingCityDataAndUriIsValid_ShouldReturnResponseData() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchForecastForCoordinates_WhenWeatherReportIsNull_ShouldThrowException_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchForecastForCoordinates_WhenWeatherReportIsEmpty_ShouldThrowException_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldReturnResponseData_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldThrowException_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}
}
