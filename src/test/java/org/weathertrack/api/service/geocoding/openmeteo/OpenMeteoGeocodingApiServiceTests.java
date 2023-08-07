package org.weathertrack.api.service.geocoding.openmeteo;

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
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataResponseDTO;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.resource.ApiMessageResource;
import org.weathertrack.model.ResponseData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
	void fetchCitiesForCityName_WhenCityNameAndUriIsValid_ShouldReturnResponseData() throws URISyntaxException, IOException, InterruptedException {
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

		String jsonResponse = "{\"results\":[{\"name\":\"TestCity\",\"population\":1000000}]}";
		InputStream inputStream = new ByteArrayInputStream(jsonResponse.getBytes(StandardCharsets.UTF_8));

		HttpResponse<InputStream> mockResponse = mock(HttpResponse.class);
		when(mockResponse.body()).thenReturn(inputStream);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockResponse);

		CityDataResponseDTO mockedResponseDTO = mock(CityDataResponseDTO.class);
		when(mockedResponseDTO.getResults()).thenReturn(new CityDataDTO[]{MOCKED_CITY_DATA_DTO});
		when(mockHttpService.parseJsonResponse(any(InputStream.class), eq(CityDataResponseDTO.class))).thenReturn(mockedResponseDTO);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}

	@Test
	void fetchCitiesForCityName_CityDataResponseDTOIsNull_ShouldThrowException_WithAppropriateMessage() throws URISyntaxException, IOException, InterruptedException {
		// When
		when(mockUriBuilder.setScheme(SCHEME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost(HOST)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath(PATH)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		mockUriBuilder.build();

		String jsonResponse = "{\"results\":[]}";
		InputStream inputStream = new ByteArrayInputStream(jsonResponse.getBytes(StandardCharsets.UTF_8));

		HttpResponse<InputStream> mockResponse = mock(HttpResponse.class);
		when(mockResponse.body()).thenReturn(inputStream);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockResponse);

		CityDataResponseDTO mockedResponseDTO = mock(CityDataResponseDTO.class);
		when(mockedResponseDTO.getResults()).thenReturn(null);
		when(mockHttpService.parseJsonResponse(any(InputStream.class), eq(CityDataResponseDTO.class))).thenReturn(mockedResponseDTO);

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
	void fetchCitiesForCityName_WhenCityDataResponseDTOIsEmpty_ShouldReturnResponseData_WithAppropriateMessage() throws URISyntaxException, IOException, InterruptedException {
		// When
		var expectedResult = new ResponseData<>(false, ApiMessageResource.NO_CITIES_FOUND, null);

		when(mockUriBuilder.setScheme(SCHEME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost(HOST)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath(PATH)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		mockUriBuilder.build();

		String jsonResponse = "{\"results\":[]}";
		InputStream inputStream = new ByteArrayInputStream(jsonResponse.getBytes(StandardCharsets.UTF_8));

		HttpResponse<InputStream> mockResponse = mock(HttpResponse.class);
		when(mockResponse.body()).thenReturn(inputStream);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockResponse);

		CityDataResponseDTO mockedResponseDTO = mock(CityDataResponseDTO.class);
		when(mockedResponseDTO.getResults()).thenReturn(new CityDataDTO[0]);
		when(mockHttpService.parseJsonResponse(any(InputStream.class), eq(CityDataResponseDTO.class))).thenReturn(mockedResponseDTO);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}

	private static Stream<Arguments> fetchCitiesForCityName_WhenStatusCodeIsReceived_ShouldReturnResponseData_WithAppropriateMessage() {
		var cityDataResponse = new ArrayList<>();
		cityDataResponse.add(MOCKED_CITY_DATA_DTO);
		return Stream.of(
				Arguments.of(200, true, null, cityDataResponse),
				Arguments.of(400, false, ApiServiceExceptionMessage.STATUS_CODE_400, null),
				Arguments.of(500, false, ApiServiceExceptionMessage.STATUS_CODE_500, null)

		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchCitiesForCityName_WhenStatusCodeIsReceived_ShouldReturnResponseData_WithAppropriateMessage(
			int statusCodeValue, boolean success, String responseMessage, ArrayList<CityDataDTO> expectedCityDataResponse) throws URISyntaxException, IOException, InterruptedException {
		// When

		var expectedResult = new ResponseData<>(success, responseMessage,
				expectedCityDataResponse
		);

		when(mockUriBuilder.setScheme(SCHEME)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setHost(HOST)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setPath(PATH)).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);
		mockUriBuilder.build();

		String jsonResponse = "{\"results\":[{\"name\":\"TestCity\",\"population\":1000000}]}";
		InputStream inputStream = new ByteArrayInputStream(jsonResponse.getBytes(StandardCharsets.UTF_8));

		HttpResponse<InputStream> mockResponse = mock(HttpResponse.class);
		when(mockResponse.body()).thenReturn(inputStream);
		when(mockResponse.statusCode()).thenReturn(statusCodeValue);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockResponse);

		CityDataResponseDTO mockedResponseDTO = mock(CityDataResponseDTO.class);
		when(mockedResponseDTO.getResults()).thenReturn(new CityDataDTO[]{MOCKED_CITY_DATA_DTO});
		when(mockHttpService.parseJsonResponse(any(InputStream.class), eq(CityDataResponseDTO.class))).thenReturn(mockedResponseDTO);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}
}
