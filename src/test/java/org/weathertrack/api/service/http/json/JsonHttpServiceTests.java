package org.weathertrack.api.service.http.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.weathertrack.logging.factory.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonHttpServiceTests {
	@Mock
	private LoggerFactory mockLogger;
	@Mock
	private HttpClient httpClient;
	@InjectMocks
	private JsonHttpService sut;

	@BeforeEach
	void setUp() {
		httpClient = mock(HttpClient.class);
		sut = new JsonHttpService(httpClient, mockLogger);
	}

	@Test
	void sendHttpGetRequest_WhenUriIsValid_ShouldReturnRequest() throws IOException, InterruptedException {
		// When
		var mockHttpResponse = mock(HttpResponse.class);

		var expectedUri = URI.create("https://geocoding-api.open-meteo.com/v1/search");
		var expectedResponse = "Mocked expected response";

		when(mockHttpResponse.body()).thenReturn(expectedResponse);
		when(httpClient.send(any(), any())).thenReturn(mockHttpResponse);

		// Given
		var result = sut.sendHttpGetRequest(expectedUri);

		// Then
		assertEquals(expectedResponse, result.body());
	}
}
