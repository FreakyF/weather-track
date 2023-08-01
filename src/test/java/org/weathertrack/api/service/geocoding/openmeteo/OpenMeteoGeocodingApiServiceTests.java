package org.weathertrack.api.service.geocoding.openmeteo;

import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
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
import static org.mockito.ArgumentMatchers.anyString;
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
	void fetchCitiesForCityName_WhenUriSyntaxIsValid_ShouldReturnBuilttUri() throws URISyntaxException {
		// When
		var mockUriBuilder = mock(URIBuilder.class);
		var expectedUri = URI.create("https://geocoding-api.open-meteo.com/v1/search?name=" + CITY_NAME_VALUE);

		when(mockUriBuilder.build()).thenReturn(expectedUri);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME_VALUE);

		// Then
		assertEquals(expectedUri, result);
	}

	@Test
	void fetchCitiesForCityName_WhenUriSyntaxIsInvalid_ShouldThrowException_WithAppropriateMessage() throws URISyntaxException {
		// When
		var mockUriBuilder = mock(URIBuilder.class);
		var expectedUri = URI.create("https://geocoding-api.open-meteo.com/v1/search?name=" + CITY_NAME_VALUE);

		when(mockUriBuilder.build()).thenReturn(expectedUri);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME_VALUE);

		// Then
		var exception = assertThrows(URISyntaxException.class, () -> sut.fetchCitiesForCityName(CITY_NAME_VALUE));
		assertEquals(ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID, exception.getMessage());
	}

	private static Stream<Arguments> fetchCitiesForCityName_WhenCityNameAndUriIsValid_ShouldReturnGeocodingCityData() {
		return Stream.of(
				Arguments.of("Kielce"),
				Arguments.of("Jastrzębie-Zdrój"),
				Arguments.of("San Francisco"),
				Arguments.of("D'Lo"),
				Arguments.of("Saint-Lô"),
				Arguments.of("Winston-Salem")
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchCitiesForCityName_WhenCityNameAndUriIsValid_ShouldReturnGeocodingCityData(String cityNameValue) {
		// When
		List<GeocodingCityData> mockResponse = new ArrayList<>();
		mockResponse.add(new GeocodingCityData("Kielce", "Świętokrzyskie", "Poland"));
		mockResponse.add(new GeocodingCityData("Jastrzębie-Zdrój", "Silesia", "Poland"));
		mockResponse.add(new GeocodingCityData("San Francisco", "California", "United States"));
		mockResponse.add(new GeocodingCityData("D'Lo", "Mississippi", "United States"));
		mockResponse.add(new GeocodingCityData("Winston-Salem", "North Carolina", "United States"));

		when(sut.fetchCitiesForCityName(anyString())).thenReturn(mockResponse);

		// Given
		var result = sut.fetchCitiesForCityName(cityNameValue);

		// Then
		assertEquals(mockResponse, result);
	}

	@Test
	void fetchCitiesForCityName_WhenGeocodingCityDataIsNull_ShouldThrowException_WithAppropriateMessage() {
		// When
		List<GeocodingCityData> mockResponse = new ArrayList<>();
		mockResponse.add(null);

		when(sut.fetchCitiesForCityName(anyString())).thenReturn(mockResponse);

		// Then
		var exception = assertThrows(NullPointerException.class, () -> sut.fetchCitiesForCityName(CITY_NAME_VALUE));
		assertEquals(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL, exception.getMessage());
	}

	@Test
	void fetchCitiesForCityName_WhenGeocodingCityDataIsEmpty_ShouldReturnGeocodingCityData() {
		// When
		List<GeocodingCityData> mockResponse = new ArrayList<>();

		when(sut.fetchCitiesForCityName(anyString())).thenReturn(mockResponse);

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
		JsonHttpService jsonHttpService = mock(JsonHttpService.class);
		when(jsonHttpService.sendHttpGetRequest(requestUrl)).thenReturn(mockResponse);

		// Then
		var exception = assertThrows(IllegalArgumentException.class, () -> sut.fetchGeocodingCityDataFromApi(requestUrl));
		assertEquals(ApiServiceExceptionMessage.STATUS_CODE_500, exception.getMessage());
	}
}
