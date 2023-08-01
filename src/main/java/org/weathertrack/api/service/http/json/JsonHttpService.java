package org.weathertrack.api.service.http.json;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.weathertrack.api.service.http.HttpService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonHttpService implements HttpService {
	private final HttpClient httpClient;

	@Inject
	public JsonHttpService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public HttpResponse<InputStream> sendHttpGetRequest(URI requestUrl) throws IOException, InterruptedException {
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(requestUrl)
				.GET()
				.build();

		return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
	}

	@Override
	public <T> T parseJsonResponse(InputStream responseBody, Type targetType) {
		try (InputStreamReader inputStreamReader = new InputStreamReader(responseBody)) {
			Gson gson = new Gson();
			return gson.fromJson(inputStreamReader, targetType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
