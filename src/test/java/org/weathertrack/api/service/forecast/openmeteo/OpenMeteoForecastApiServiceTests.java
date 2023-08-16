package org.weathertrack.api.service.forecast.openmeteo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenMeteoForecastApiServiceTests {
	private AutoCloseable closeable;
	@InjectMocks
	private OpenMeteoForecastApiService sut;

	@BeforeEach
	void beforeEach() {
		closeable = MockitoAnnotations.openMocks(this);
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
