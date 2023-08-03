package org.weathertrack.api.service.geocoding.openmeteo;

import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.model.ResponseData;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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
		// When
		var cityName = "Kielce";
		var syntaxException = new URISyntaxException(cityName, ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID);

		when(mockUriBuilder.setParameter("name", cityName)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.build()).thenThrow(syntaxException);

		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchCitiesForCityName(cityName),
				"Expected fetchCitiesForCityName to throw Exception, but it didn't"
		);

		// Then
		assertTrue(thrown instanceof RuntimeException, "Expected IllegalArgumentException");
		assertEquals(IllegalArgumentException.class, thrown.getClass());
		assertEquals(ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID, thrown.getMessage());
	}

	@Test
	void fetchCitiesForCityName_WhenCityNameAndUriIsValid_ShouldReturnResponseData() throws URISyntaxException {
		// When
		var cityName = "Kielce";

		List<CityDataDTO> expectedCityDataResponse = new ArrayList<>();
		expectedCityDataResponse.add(
				new CityDataDTO(
						769250L,
						"Kielce",
						50.87033,
						20.62752,
						267.0,
						"PPLA",
						"PL",
						858790L,
						7530962L,
						7531642L,
						0,
						"Europe/Warsaw",
						208598L,
						null,
						798544,
						"Poland",
						"Świętokrzyskie",
						"Kielce",
						"Kielce",
						null
				)
		);

		ResponseData<List<CityDataDTO>> expectedResult = new ResponseData<>(false, null,
				expectedCityDataResponse
		);

		when(mockUriBuilder.setScheme("https")).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost("geocoding-api.open-meteo.com")).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath("/v1/search")).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", cityName)).thenReturn(mockUriBuilder);
		mockUriBuilder.build();

		// Given
		var result = sut.fetchCitiesForCityName(cityName);

		// Then
		assertEquals(expectedResult, result);
	}

	@Test
	void fetchCitiesForCityName_CityDataResponseDTOIsNull_ShouldThrowException_WithAppropriateMessage() throws URISyntaxException {
		// When
		var cityName = "Kielce";

		when(mockUriBuilder.setScheme("https")).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost("geocoding-api.open-meteo.com")).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath("/v1/search")).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", cityName)).thenReturn(mockUriBuilder);
		var validUrl = mockUriBuilder.build();

		when(sut.fetchCityDataFromApi(validUrl)).thenReturn(null);

		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchCitiesForCityName(cityName),
				"Expected fetchCitiesForCityName to throw Exception, but it didn't"
		);

		// Then
		assertTrue(thrown instanceof RuntimeException, "Expected NullPointerException");
		assertEquals(NullPointerException.class, thrown.getClass());
		assertEquals(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL, thrown.getMessage());
	}

	@Test
	void fetchCitiesForCityName_WhenCityDataResponseDTOIsEmpty_ShouldReturnResponseData_WithAppropriateMessage() throws URISyntaxException {
		// When
		var cityName = "Kielce";
		var validUrl = URI.create("https://geocoding-api.open-meteo.com/v1/search");

		var expectedCityData = new ArrayList<CityDataDTO>();
		var expectedResult = new ResponseData<>(false, "No cities found!", expectedCityData);

		when(mockUriBuilder.setParameter("name", cityName)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.build()).thenReturn(validUrl);

		when(sut.fetchCityDataFromApi(validUrl)).thenReturn(expectedCityData);

		// Given
		var result = sut.fetchCitiesForCityName(cityName);

		// Then
		assertEquals(expectedResult, result);
	}

	@Test
	void fetchCitiesForCityName_WhenStatusCodeIs200_ShouldReturnResponseBody() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchCitiesForCityName_WhenStatusCodeIs400_ShouldThrowException_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}

	@Test
	void fetchCitiesForCityName_WhenStatusCodeIs500_ShouldThrowException_WithAppropriateMessage() {
		// When

		// Given

		// Then
	}
}
