package org.weathertrack.api.service.forecast.openmeteo;

import org.apache.hc.core5.net.URIBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.weathertrack.TestData;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.forecast.openmeteo.model.WeatherReport;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenMeteoForecastApiServiceTests {
	private static WeatherReport MOCKED_WEATHER_REPORT;
	private static GeocodingCityData MOCKED_GEOCODING_CITY_DATA;
	@Mock
	private URIBuilder mockUriBuilder;
	private AutoCloseable closeable;
	@InjectMocks
	private OpenMeteoForecastApiService sut;

	@BeforeEach
	void beforeEach() {
		closeable = MockitoAnnotations.openMocks(this);
		MOCKED_WEATHER_REPORT = TestData.Provider.createWeatherReport();
		MOCKED_GEOCODING_CITY_DATA = TestData.Provider.createCityDataDTO();
		sut = new OpenMeteoForecastApiService();
	}

	@AfterEach
	void afterEach() throws Exception {
		closeable.close();
	}

	@Test
	void fetchForecastForCoordinates_WhenGeocodingCityDataIsNull_ShouldThrowNullPointerException() {
		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchForecastForCoordinates(null),
				"Expected fetchForecastForCoordinates to throw Exception, but it didn't"
		);

		// Then
		assertTrue(thrown instanceof RuntimeException, "Expected NullPointerException");
		assertEquals(NullPointerException.class, thrown.getClass());
		assertEquals(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL, thrown.getMessage());
	}

	@Test
	void fetchForecastForCoordinates_WhenUriSyntaxIsInvalid_ShouldThrowIllegalArgumentException() throws URISyntaxException {
		// When
		var latitude = "21";
		var syntaxException = new URISyntaxException(latitude, ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID);

		when(mockUriBuilder.setParameter("latitude", latitude)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.build()).thenThrow(syntaxException);

		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchForecastForCoordinates(MOCKED_GEOCODING_CITY_DATA),
				"Expected fetchForecastForCoordinates to throw Exception, but it didn't"
		);

		// Then
		assertTrue(thrown instanceof RuntimeException, "Expected IllegalArgumentException");
		assertEquals(IllegalArgumentException.class, thrown.getClass());
		assertEquals(ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID, thrown.getMessage());
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
	void fetchForecastForCoordinates_WhenWeatherReportIsEmpty_ShouldReturnFailureResponseData() {
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
