package org.weathertrack.api.service.http.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JsonHttpServiceTests {
	private JsonHttpService sut;

	@BeforeEach
	void setUp() {
		sut = new JsonHttpService();
	}

	@Test
	void sendHttpGetRequest_WhenUriIsValid_ShouldReturnRequest() throws IOException, InterruptedException {
		// When
		var mockHttpClient = mock(HttpClient.class);
		var mockHttpResponse = mock(HttpResponse.class);

		var expectedUri = URI.create("https://geocoding-api.open-meteo.com/v1/search");
		var expectedResponse = "Mocked expected response";

		when(mockHttpResponse.body()).thenReturn(expectedResponse);
		when(mockHttpClient.send(any(), any())).thenReturn(mockHttpResponse);

		// Given
		var result = sut.sendHttpGetRequest(expectedUri);

		String responseBody;
		try (InputStream inputStream = result.body()) {
			responseBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		}

		// Then
		assertEquals(expectedResponse, responseBody);
	}
}
