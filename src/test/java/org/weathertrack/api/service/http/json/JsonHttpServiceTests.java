package org.weathertrack.api.service.http.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.weathertrack.TestData;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.http.exception.ParseJsonException;
import org.weathertrack.logging.factory.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonHttpServiceTests {
	private GeocodingCityData MOCKED_GEOCODING_CITY_DATA;
	@Mock
	private LoggerFactory mockLogger;
	@Mock
	private HttpClient mockhttpClient;

	@Mock
	private HttpResponse<Object> mockHttpResponse;
	@InjectMocks
	private JsonHttpService sut;

	@BeforeEach
	void setUp() {
		sut = new JsonHttpService(mockhttpClient, mockLogger);
		MOCKED_GEOCODING_CITY_DATA = TestData.Provider.createGeocodingCityData();
	}

	@Test
	void sendHttpGetRequest_WhenUriIsValid_ShouldReturnRequest() throws IOException, InterruptedException {
		// When

		var expectedUri = URI.create("https://geocoding-api.open-meteo.com/v1/search");
		var expectedResponse = "Mocked expected response";

		try (InputStream expectedResponseStream = new ByteArrayInputStream(expectedResponse.getBytes())) {
			when(mockHttpResponse.body()).thenReturn(expectedResponseStream);
			when(mockhttpClient.send(any(), any())).thenReturn(mockHttpResponse);

			// Given
			var result = sut.sendHttpGetRequest(expectedUri);

			String resultString;
			try (InputStream resultStream = result.body()) {
				resultString = new String(resultStream.readAllBytes(), StandardCharsets.UTF_8);
			}

			// Then
			assertEquals(expectedResponse, resultString);
		}
	}

	@Test
	void parseJsonResponse_WhenFailsToParse_ShouldThrowParseJsonException() {
		var invalidJson = "invalid_json";
		var responseBody = new ByteArrayInputStream(invalidJson.getBytes());
		var clazz = GeocodingCityData.class;

		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> sut.parseJsonResponse(responseBody, clazz),
				"Expected parseJsonResponse to throw Exception, but it didn't"
		);

		// Then
		assertEquals(ParseJsonException.class, thrown.getClass());
		assertEquals("java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $", thrown.getMessage());
	}

	@Test
	void parseJsonResponse_WhenParseSuccess_ShouldReturnParsedJson() throws ParseJsonException {
		// When
		var expectedResult = MOCKED_GEOCODING_CITY_DATA;
		var validJson = "{\"name\":\"Kielce\",\"administration\":\"Świętokrzyskie\",\"country\":\"Poland\",\"latitude\":21.0,\"longitude\":37.0}";

		var responseBody = new ByteArrayInputStream(validJson.getBytes());
		var clazz = GeocodingCityData.class;

		// Given
		var result = sut.parseJsonResponse(responseBody, clazz);

		// Then
		assertEquals(expectedResult, result);
	}
}
