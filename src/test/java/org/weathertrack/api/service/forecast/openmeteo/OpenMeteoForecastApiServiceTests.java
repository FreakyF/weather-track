package org.weathertrack.api.service.forecast.openmeteo;

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
import org.weathertrack.api.service.forecast.openmeteo.model.WeatherReport;
import org.weathertrack.api.service.forecast.openmeteo.model.WeatherReportResponseDTO;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataResponseDTO;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.resource.StatusCodesResource;
import org.weathertrack.model.ResponseData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
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

@ExtendWith(MockitoExtension.class)
class OpenMeteoForecastApiServiceTests {
	private static WeatherReport MOCKED_WEATHER_REPORT;
	private static GeocodingCityData MOCKED_GEOCODING_CITY_DATA;
	@Mock
	private URIBuilder mockUriBuilder;
	@Mock
	private HttpService mockHttpService;
	@Mock
	private HttpResponse<InputStream> mockHttpResponse;
	private AutoCloseable closeable;
	@InjectMocks
	private OpenMeteoForecastApiService sut;

	@BeforeEach
	void beforeEach() {
		closeable = MockitoAnnotations.openMocks(this);
		MOCKED_WEATHER_REPORT = TestData.Provider.createWeatherReport();
		MOCKED_GEOCODING_CITY_DATA = TestData.Provider.createCityDataDTO();
		sut = new OpenMeteoForecastApiService(mockUriBuilder, mockHttpService);
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
	void fetchForecastForCoordinates_WhenGeocodingCityDataAndUriIsValid_ShouldReturnSuccessfulResponseData() throws IOException, InterruptedException, BadRequestException, NotFoundException {
		// When
		var expectedWeatherReportResponse = new ArrayList<>();
		expectedWeatherReportResponse.add(MOCKED_WEATHER_REPORT);

		var expectedResult = new ResponseData<>(true, null,
				expectedWeatherReportResponse
		);

		buildMockUri();

		var jsonResponse = "{\"results\":[{\"name\":\"TestCity\",\"population\":1000000}]}";
		mockHttpResponse(jsonResponse);

		// Given
		var result = sut.fetchForecastForCoordinates(MOCKED_GEOCODING_CITY_DATA);

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
	void fetchForecastForCoordinates_WhenWeatherReportIsNull_ShouldThrowNullPointerException() throws IOException, InterruptedException {
		// When
		buildMockUri();

		var jsonResponse = "{\"results\":[]}";
		mockHttpResponse(jsonResponse);

		var mockedResponseDTO = mock(WeatherReportResponseDTO.class);
		when(mockedResponseDTO.getResults()).thenReturn(null);
		when(mockHttpService.parseJsonResponse(any(InputStream.class), eq(CityDataResponseDTO.class))).thenReturn(mockedResponseDTO);

		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchForecastForCoordinates(MOCKED_GEOCODING_CITY_DATA),
				"Expected fetchCitiesForCityName to throw Exception, but it didn't"
		);

		// Then
		assertTrue(thrown instanceof RuntimeException, "Expected NullPointerException");
		assertEquals(NullPointerException.class, thrown.getClass());
		assertEquals(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL, thrown.getMessage());
	}

	private void buildMockUri() {
		when(mockUriBuilder.setParameter("latitude", "21")).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("longitude", "37")).thenReturn(mockUriBuilder);
		when(mockUriBuilder.setParameter("hourly", "temperature_2m")).thenReturn(mockUriBuilder);
	}

	private static Stream<Arguments> fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldReturnResponseData() {
		return Stream.of(
				Arguments.of(500, false, StatusCodesResource.STATUS_CODE_500, null),
				Arguments.of(503, false, StatusCodesResource.STATUS_CODE_503, null),
				Arguments.of(504, false, StatusCodesResource.STATUS_CODE_504, null)
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldReturnResponseData(
			int statusCodeValue, boolean success, String responseMessage, ArrayList<GeocodingCityData> expectedCityDataResponse) throws IOException, InterruptedException, BadRequestException, NotFoundException {
		// When
		var expectedResult = new ResponseData<>(success, responseMessage,
				expectedCityDataResponse
		);

		buildMockUri();

		when(mockHttpResponse.statusCode()).thenReturn(statusCodeValue);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockHttpResponse);

		// Given
		var result = sut.fetchForecastForCoordinates(MOCKED_GEOCODING_CITY_DATA);

		// Then
		assertEquals(expectedResult, result);
	}

	private static Stream<Arguments> fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldThrowException() {
		return Stream.of(
				Arguments.of(400, ApiServiceExceptionMessage.STATUS_CODE_400, BadRequestException.class),
				Arguments.of(404, ApiServiceExceptionMessage.STATUS_CODE_404, NotFoundException.class)
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchForecastForCoordinates_WhenStatusCodeIsReceived_ShouldThrowException(
			int statusCodeValue, String exceptionMessage, Class<? extends Exception> exceptionClass) throws IOException, InterruptedException {
		// When
		buildMockUri();

		when(mockHttpResponse.statusCode()).thenReturn(statusCodeValue);
		when(mockHttpService.sendHttpGetRequest(any())).thenReturn(mockHttpResponse);

		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.fetchForecastForCoordinates(MOCKED_GEOCODING_CITY_DATA),
				"Expected fetchCitiesForCityName to throw Exception, but it didn't"
		);

		// Then
		assertEquals(exceptionClass, thrown.getClass());
		assertEquals(exceptionMessage, thrown.getMessage());
	}
}
