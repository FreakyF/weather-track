package org.weathertrack.api.service.http.json;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.http.exception.ParseJsonException;
import org.weathertrack.logging.Logger;
import org.weathertrack.logging.factory.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonHttpService implements HttpService {
	private final HttpClient httpClient;
	private final Logger<JsonHttpService> logger;
	private final Gson gson;

	@Inject
	public JsonHttpService(HttpClient httpClient, LoggerFactory logger) {
		this.httpClient = httpClient;
		this.gson = new Gson();
		this.logger = logger.create(JsonHttpService.class);
	}

	@Override
	public HttpResponse<InputStream> sendHttpGetRequest(URI requestUrl) throws IOException, InterruptedException {
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(requestUrl)
				.GET()
				.build();

		return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
	}

	public <T> T parseJsonResponse(InputStream responseBody, Class<T> clazz) throws ParseJsonException {
		T parsedJson;
		try (InputStreamReader inputStreamReader = new InputStreamReader(responseBody)) {
			parsedJson = gson.fromJson(inputStreamReader, clazz);
		} catch (Exception e) {
			throw new ParseJsonException(e.getMessage(), e);
		}
		return parsedJson;
	}
}
