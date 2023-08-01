package org.weathertrack.api.service.geocoding.openmeteo;

import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.http.HttpService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenMeteoGeocodingApiServiceTests {
	private OpenMeteoGeocodingApiService sut;
	private URIBuilder mockUriBuilder;
	private HttpClient mockHttpClient;
	private HttpService mockHttpService;

	@BeforeEach
	void beforeEach() {
		mockUriBuilder = mock(URIBuilder.class);
		mockHttpClient = mock(HttpClient.class);
		mockHttpService = mock(HttpService.class);
		sut = new OpenMeteoGeocodingApiService(mockUriBuilder, mockHttpClient, mockHttpService);
	}

	private static Stream<Arguments> fetchCitiesForCityName_WhenCityNameIsInvalid_ShouldThrowException_WithAppropriateMessage() {
		return Stream.of(
				Arguments.of(null, ApiServiceExceptionMessage.CITY_NAME_IS_NULL, NullPointerException.class),
				Arguments.of("", ApiServiceExceptionMessage.CITY_NAME_IS_BLANK, IllegalArgumentException.class),
				Arguments.of(" ", ApiServiceExceptionMessage.CITY_NAME_IS_BLANK, IllegalArgumentException.class)
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchCitiesForCityName_WhenCityNameIsInvalid_ShouldThrowException_WithAppropriateMessage(
			String cityNameValue, String expectedExceptionMessage, Class<? extends Throwable> exceptionClass) {
		// Then
		var exception = assertThrows(exceptionClass, () -> sut.fetchCitiesForCityName(cityNameValue));
		assertEquals(expectedExceptionMessage, exception.getMessage());
	}

	@Test
	void fetchCitiesForCityName_WhenUriSyntaxIsInvalid_ShouldThrowException_WithAppropriateMessage() throws URISyntaxException {
		// Arrange
		var cityName = "New York";
		var syntaxException = new URISyntaxException(cityName, "Invalid URI syntax");

		when(mockUriBuilder.setParameter("name", cityName)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.build()).thenThrow(syntaxException);

		// Act
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchCitiesForCityName(cityName),
				"Expected fetchCitiesForCityName to throw Exception, but it didn't"
		);

		// Assert
		assertTrue(thrown instanceof RuntimeException, "Expected IllegalArgumentException");
		assertEquals(RuntimeException.class, thrown.getClass());
		assertEquals("Invalid URI syntax: New York", thrown.getCause().getMessage());
	}

	private static Stream<Arguments> fetchCitiesForCityName_WhenCityNameAndUriIsValid_ShouldReturnGeocodingCityData() {
		return Stream.of(
				Arguments.of("Kielce", "Świętokrzyskie", "Poland"),
				Arguments.of("Jastrzębie-Zdrój", "Silesia", "Poland"),
				Arguments.of("San Francisco", "California", "United States"),
				Arguments.of("D'Lo", "Mississippi", "United States"),
				Arguments.of("Saint-Lô", "Normandy", "France"),
				Arguments.of("Winston-Salem", "North Carolina", "United States")
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchCitiesForCityName_WhenCityNameAndUriIsValid_ShouldReturnGeocodingCityData(String cityNameValue, String administrationValue, String CountryValue) {
		// When

		// Given

		// Then
	}

	@Test
	void fetchCitiesForCityName_WhenGeocodingCityDataIsNull_ShouldThrowException_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchCitiesForCityName_WhenGeocodingCityDataIsEmpty_ShouldThrowException_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchGeocodingCityDataFromApi_WhenStatusCodeIs200_ShouldReturnResponseBody() throws IOException, InterruptedException {
		// When

		// Given

		// Then
	}

	@Test
	void fetchGeocodingCityDataFromApi_WhenStatusCodeIs400_ShouldThrowException_WithAppropriateMessage() throws IOException, InterruptedException {
		// When

		// Given

		// Then
	}

	@Test
	void fetchGeocodingCityDataFromApi_WhenStatusCodeIs500_ShouldThrowException_WithAppropriateMessage() throws IOException, InterruptedException {
		// When

		// Given

		// Then
	}
}
