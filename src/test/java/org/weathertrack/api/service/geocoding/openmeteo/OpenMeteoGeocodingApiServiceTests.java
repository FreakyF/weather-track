package org.weathertrack.api.service.geocoding.openmeteo;

import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.http.json.JsonHttpService;
import org.weathertrack.geocoding.model.GeocodingCityData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenMeteoGeocodingApiServiceTests {

	private static final String CITY_NAME_VALUE = "Kielce";
	private OpenMeteoGeocodingApiService sut;

	@BeforeEach
	void beforeEach() {
		sut = new OpenMeteoGeocodingApiService();
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
		// When
		var mockUriBuilder = mock(URIBuilder.class);
		var expectedUri = URI.create("https://geocoding-api.open-meteo.com/v1/search?name=" + CITY_NAME_VALUE);

		when(mockUriBuilder.build()).thenReturn(expectedUri);

		// Given
		sut.fetchCitiesForCityName(CITY_NAME_VALUE);

		// Then
		var exception = assertThrows(URISyntaxException.class, () -> sut.fetchCitiesForCityName(CITY_NAME_VALUE));
		assertEquals(ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID, exception.getMessage());
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
		var sut = mock(OpenMeteoGeocodingApiService.class);
		List<GeocodingCityData> mockResponse = new ArrayList<>();
		mockResponse.add(new GeocodingCityData(cityNameValue, administrationValue, CountryValue));

		when(sut.fetchCitiesForCityName(cityNameValue)).thenReturn(mockResponse);

		// Given
		var result = sut.fetchCitiesForCityName(cityNameValue);

		// Then
		assertEquals(mockResponse, result);
	}

	@Test
	void fetchCitiesForCityName_WhenGeocodingCityDataIsNull_ShouldThrowException_WithAppropriateMessage() {
		// When
		var sut = mock(OpenMeteoGeocodingApiService.class);
		List<GeocodingCityData> mockResponse = new ArrayList<>();
		mockResponse.add(null);

		when(sut.fetchCitiesForCityName(CITY_NAME_VALUE)).thenReturn(mockResponse);

		// Then
		var exception = assertThrows(NullPointerException.class, () -> sut.fetchCitiesForCityName(CITY_NAME_VALUE));
		assertEquals(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL, exception.getMessage());
	}

	@Test
	void fetchCitiesForCityName_WhenGeocodingCityDataIsEmpty_ShouldReturnGeocodingCityData() {
		// When
		var sut = mock(OpenMeteoGeocodingApiService.class);
		List<GeocodingCityData> mockResponse = new ArrayList<>();

		when(sut.fetchCitiesForCityName(CITY_NAME_VALUE)).thenReturn(mockResponse);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME_VALUE);

		// Then
		assertEquals(mockResponse, result);
	}

	@Test
	void fetchGeocodingCityDataFromApi_WhenStatusCodeIs200_ShouldReturnResponseBody() throws IOException, InterruptedException {
		// When
		var requestUrl = URI.create("https://geocoding-api.open-meteo.com/v1/search?name=" + CITY_NAME_VALUE);
		HttpResponse<InputStream> mockStatusCode = mock(HttpResponse.class);
		when(mockStatusCode.statusCode()).thenReturn(200);

		List<GeocodingCityData> mockResponse = new ArrayList<>();
		mockResponse.add(new GeocodingCityData("Kielce", "Świętokrzyskie", "Poland"));

		JsonHttpService jsonHttpService = mock(JsonHttpService.class);
		when(jsonHttpService.sendHttpGetRequest(requestUrl)).thenReturn(mockStatusCode);

		// Given
		var result = sut.fetchGeocodingCityDataFromApi(requestUrl);

		// Then
		assertEquals(mockResponse, result);
	}

	@Test
	void fetchGeocodingCityDataFromApi_WhenStatusCodeIs400_ShouldThrowException_WithAppropriateMessage() throws IOException, InterruptedException {
		// When
		var requestUrl = URI.create("https://geocoding-api.open-meteo.com/v1/search?name=" + CITY_NAME_VALUE);
		HttpResponse<InputStream> mockResponse = mock(HttpResponse.class);
		when(mockResponse.statusCode()).thenReturn(400);
		JsonHttpService jsonHttpService = mock(JsonHttpService.class);
		when(jsonHttpService.sendHttpGetRequest(requestUrl)).thenReturn(mockResponse);

		// Then
		var exception = assertThrows(IllegalArgumentException.class, () -> sut.fetchGeocodingCityDataFromApi(requestUrl));
		assertEquals(ApiServiceExceptionMessage.STATUS_CODE_400, exception.getMessage());
	}

	@Test
	void fetchGeocodingCityDataFromApi_WhenStatusCodeIs500_ShouldThrowException_WithAppropriateMessage() throws IOException, InterruptedException {
		// When
		var requestUrl = URI.create("https://geocoding-api.open-meteo.com/v1/search?name=" + CITY_NAME_VALUE);
		HttpResponse<InputStream> mockResponse = mock(HttpResponse.class);
		when(mockResponse.statusCode()).thenReturn(500);
		HttpService httpService = mock(HttpService.class);
		when(httpService.sendHttpGetRequest(requestUrl)).thenReturn(mockResponse);

		// Then
		var exception = assertThrows(IllegalArgumentException.class, () -> sut.fetchGeocodingCityDataFromApi(requestUrl));
		assertEquals(ApiServiceExceptionMessage.STATUS_CODE_500, exception.getMessage());
	}
}
