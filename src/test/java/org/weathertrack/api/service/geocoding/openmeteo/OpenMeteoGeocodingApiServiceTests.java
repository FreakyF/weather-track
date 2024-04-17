package org.weathertrack.api.service.geocoding.openmeteo;

import org.apache.hc.core5.http.HttpStatus;
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
import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataResponseDTO;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.http.exception.ParseJsonException;
import org.weathertrack.api.service.resource.ApiMessageResource;
import org.weathertrack.api.service.resource.StatusCodesResource;
import org.weathertrack.model.ResponseData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenMeteoGeocodingApiServiceTests {
	private final static String CITY_NAME = "Kielce";
	private static CityDataDTO MOCKED_CITY_DATA_DTO;
	private static GeocodingCityData MOCKED_GEOCODING_CITY_DATA_DTO;
	@Mock
	private URIBuilder mockUriBuilder;
	@Mock
	private HttpService mockHttpService;
	@Mock
	private HttpResponse<InputStream> mockHttpResponse;
	private AutoCloseable closeable;
	@InjectMocks
	private OpenMeteoGeocodingApiService sut;

	@BeforeEach
	void beforeEach() {
		closeable = MockitoAnnotations.openMocks(this);
		MOCKED_CITY_DATA_DTO = TestData.Provider.createCityDataDTO();
		MOCKED_GEOCODING_CITY_DATA_DTO = TestData.Provider.createGeocodingCityData();
		sut = new OpenMeteoGeocodingApiService(mockUriBuilder, mockHttpService);
	}

	@AfterEach
	void afterEach() throws Exception {
		closeable.close();
	}

	private static Stream<Arguments> fetchCitiesForCityName_WhenCityNameIsInvalid_ShouldThrowException() {
		return Stream.of(
				Arguments.of(null, ApiServiceExceptionMessage.CITY_NAME_IS_NULL, IllegalArgumentException.class),
				Arguments.of("", ApiServiceExceptionMessage.CITY_NAME_IS_BLANK, IllegalArgumentException.class),
				Arguments.of(" ", ApiServiceExceptionMessage.CITY_NAME_IS_BLANK, IllegalArgumentException.class)
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchCitiesForCityName_WhenCityNameIsInvalid_ShouldThrowException(
			String cityNameValue, String expectedExceptionMessage, Class<? extends Throwable> exceptionClass) {
		// When
		var exception = assertThrows(exceptionClass, () -> sut.fetchCitiesForCityName(cityNameValue));

		// Then
		assertEquals(expectedExceptionMessage, exception.getMessage());
	}

	@Test
	void fetchCitiesForCityName_WhenUriSyntaxIsInvalid_ShouldThrowException() throws URISyntaxException {
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
	void fetchCitiesForCityName_WhenCityNameAndUriIsValid_ShouldReturnResponseData() throws IOException, InterruptedException, BadRequestException, NotFoundException, ParseJsonException {
		// When
		List<GeocodingCityData> expectedCityDataResponse = new ArrayList<>();
		expectedCityDataResponse.add(MOCKED_GEOCODING_CITY_DATA_DTO);

		var expectedResult = new ResponseData<>(true, null,
				expectedCityDataResponse
		);

		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);

		var jsonResponse = "{\"results\":[{\"name\":\"TestCity\",\"population\":1000000}]}";
		mockHttpResponse(jsonResponse);

		var mockedResponseDTO = mock(CityDataResponseDTO.class);
		when(mockedResponseDTO.getResults()).thenReturn(List.of(MOCKED_CITY_DATA_DTO));
		when(mockHttpService.parseJsonResponse(any(InputStream.class), eq(CityDataResponseDTO.class))).thenReturn(mockedResponseDTO);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}

	private void mockHttpResponse(String jsonResponse) throws IOException, InterruptedException {
		var inputStream = new ByteArrayInputStream(jsonResponse.getBytes(StandardCharsets.UTF_8));

		when(mockHttpResponse.body()).thenReturn(inputStream);
		when(mockHttpResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockHttpResponse);
	}

	@Test
	void fetchCitiesForCityName_CityDataResponseDTOIsNull_ShouldThrowException() throws IOException, InterruptedException, ParseJsonException {
		// When
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);

		var jsonResponse = "{\"results\":[]}";
		mockHttpResponse(jsonResponse);

		var mockedResponseDTO = mock(CityDataResponseDTO.class);
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
	void fetchCitiesForCityName_WhenCityDataResponseDTOIsEmpty_ShouldReturnResponseData() throws IOException, InterruptedException, BadRequestException, NotFoundException, ParseJsonException {
		// When
		var expectedResult = new ResponseData<>(false, ApiMessageResource.NO_CITIES_FOUND, null);

		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);

		var jsonResponse = "{\"results\":[]}";
		var inputStream = new ByteArrayInputStream(jsonResponse.getBytes(StandardCharsets.UTF_8));

		when(mockHttpResponse.body()).thenReturn(inputStream);
		when(mockHttpResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockHttpResponse);

		var mockedResponseDTO = mock(CityDataResponseDTO.class);
		when(mockedResponseDTO.getResults()).thenReturn(List.of());
		when(mockHttpService.parseJsonResponse(any(InputStream.class), eq(CityDataResponseDTO.class))).thenReturn(mockedResponseDTO);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}

	private static Stream<Arguments> fetchCitiesForCityName_WhenStatusCodeIsReceived_ShouldReturnResponseData() {
		return Stream.of(
				Arguments.of(500, false, StatusCodesResource.STATUS_CODE_500, null),
				Arguments.of(503, false, StatusCodesResource.STATUS_CODE_503, null),
				Arguments.of(504, false, StatusCodesResource.STATUS_CODE_504, null)
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchCitiesForCityName_WhenStatusCodeIsReceived_ShouldReturnResponseData(
			int statusCodeValue, boolean success, String responseMessage, ArrayList<GeocodingCityData> expectedCityDataResponse) throws IOException, InterruptedException, BadRequestException, NotFoundException {
		// When
		var expectedResult = new ResponseData<>(success, responseMessage,
				expectedCityDataResponse
		);

		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);

		when(mockHttpResponse.statusCode()).thenReturn(statusCodeValue);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockHttpResponse);

		// Given
		var result = sut.fetchCitiesForCityName(CITY_NAME);

		// Then
		assertEquals(expectedResult, result);
	}

	private static Stream<Arguments> fetchCitiesForCityName_WhenStatusCodeIsReceived_ShouldThrowException() {
		return Stream.of(
				Arguments.of(400, ApiServiceExceptionMessage.STATUS_CODE_400, BadRequestException.class),
				Arguments.of(404, ApiServiceExceptionMessage.STATUS_CODE_404, NotFoundException.class)
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchCitiesForCityName_WhenStatusCodeIsReceived_ShouldThrowException(
			int statusCodeValue, String exceptionMessage, Class<? extends Exception> exceptionClass) throws IOException, InterruptedException {
		// When
		when(mockUriBuilder.setParameter("name", CITY_NAME)).thenReturn(mockUriBuilder);

		when(mockHttpResponse.statusCode()).thenReturn(statusCodeValue);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockHttpResponse);

		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchCitiesForCityName(CITY_NAME),
				"Expected fetchCitiesForCityName to throw Exception, but it didn't"
		);

		// Then
		assertEquals(exceptionClass, thrown.getClass());
		assertEquals(exceptionMessage, thrown.getMessage());
	}
}
