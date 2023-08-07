package org.weathertrack.api.service.geocoding.openmeteo;

import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.weathertrack.TestData;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.resource.ApiMessageResource;
import org.weathertrack.model.ResponseData;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenMeteoGeocodingApiServiceTests {
	private final static String SCHEME = "https";
	private final static String HOST = "www.testhost.com";
	private final static String PATH = "/v1/search";
	private final static String CITY_NAME = "Kielce";
	private static CityDataDTO MOCKED_CITY_DATA_DTO;
	private OpenMeteoGeocodingApiService sut;
	private URIBuilder mockUriBuilder;
	private HttpClient mockHttpClient;
	private HttpService mockHttpService;
	private HttpResponse<InputStream> mockHttpResponse;

	@BeforeAll
	static void beforeAll() {
		MOCKED_CITY_DATA_DTO = TestData.Provider.createCityDataDTO();
	}

	@BeforeEach
	void beforeEach() {
		mockUriBuilder = mock(URIBuilder.class);
		mockHttpClient = mock(HttpClient.class);
		mockHttpService = mock(HttpService.class);
		mockHttpResponse = mock(HttpResponse.class);
		sut = new OpenMeteoGeocodingApiService(mockUriBuilder, mockHttpClient, mockHttpService);
	}

	private static Stream<Arguments> fetchCitiesForCityName_WhenCityNameIsInvalid_ShouldThrowException_WithAppropriateMessage() {
		return Stream.of(
				Arguments.of(null, ApiServiceExceptionMessage.CITY_NAME_IS_NULL, IllegalArgumentException.class),
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
		var syntaxException = new URISyntaxException(CITY_NAME, ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID);

		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.build()).thenThrow(syntaxException);

		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchCitiesForCityName(CITY_NAME),
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
		var expectedCityDataResponse = new ArrayList<>();
		expectedCityDataResponse.add(MOCKED_CITY_DATA_DTO);

		var expectedResult = new ResponseData<>(true, null,
				expectedCityDataResponse
		);

		when(mockUriBuilder.setScheme(SCHEME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost(HOST)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath(PATH)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		mockUriBuilder.build();

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}

	@Test
	void fetchCitiesForCityName_CityDataResponseDTOIsNull_ShouldThrowException_WithAppropriateMessage() throws URISyntaxException {
		// When
		when(mockUriBuilder.setScheme(SCHEME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost(HOST)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath(PATH)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		var validUrl = mockUriBuilder.build();

		when(sut.fetchCityDataFromApiResponse(validUrl)).thenReturn(null);

		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchCitiesForCityName(CITY_NAME),
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
		var expectedCityData = new ArrayList<CityDataDTO>();
		var expectedResult = new ResponseData<>(false, ApiMessageResource.NO_CITIES_FOUND, expectedCityData);

		when(mockUriBuilder.setScheme(SCHEME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost(HOST)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath(PATH)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		var validUrl = mockUriBuilder.build();

		when(sut.fetchCityDataFromApiResponse(validUrl)).thenReturn(expectedCityData);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}

	@Test
	void fetchCitiesForCityName_WhenStatusCodeIs200_ShouldReturnResponseData() throws URISyntaxException {
		// When
		var expectedCityDataResponse = new ArrayList<>();
		expectedCityDataResponse.add(MOCKED_CITY_DATA_DTO);

		var expectedResult = new ResponseData<>(true, null,
				expectedCityDataResponse
		);

		when(mockUriBuilder.setScheme(SCHEME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost(HOST)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath(PATH)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		mockUriBuilder.build();

		when(sut.HandleStatusCode(mockHttpResponse)).thenReturn(null);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}

	@Test
	void fetchCitiesForCityName_WhenStatusCodeIs400_ShouldReturnResponseData_WithAppropriateMessage() throws URISyntaxException {
		// When
		var expectedResult = new ResponseData<>(false, ApiServiceExceptionMessage.STATUS_CODE_400,
				null
		);

		when(mockUriBuilder.setScheme(SCHEME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost(HOST)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath(PATH)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		mockUriBuilder.build();

		when(sut.HandleStatusCode(mockHttpResponse)).thenReturn(null);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}

	@Test
	void fetchCitiesForCityName_WhenStatusCodeIs500_ShouldReturnResponseData_WithAppropriateMessage() throws URISyntaxException {
		// When
		var expectedResult = new ResponseData<>(false, ApiServiceExceptionMessage.STATUS_CODE_500,
				null
		);

		when(mockUriBuilder.setScheme(SCHEME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost(HOST)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath(PATH)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		mockUriBuilder.build();

		when(sut.HandleStatusCode(mockHttpResponse)).thenReturn(null);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}
}
