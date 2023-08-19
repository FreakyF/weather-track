package org.weathertrack.api.service.http.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

			// Convert the InputStream from the result to a String
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
		String invalidJson = "invalid_json";
		InputStream responseBody = new ByteArrayInputStream(invalidJson.getBytes());
		Class<?> clazz = GeocodingCityData.class;

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
	void parseJsonResponse_WhenParseSuccess_ShouldReturnParsedJson() {
		// When

		// Given

		// Then
	}
}
