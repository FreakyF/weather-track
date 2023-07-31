package org.weathertrack.api.service.http.json;

import org.weathertrack.api.service.http.HttpService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class JsonHttpService implements HttpService {
	private final HttpClient httpClient;

	public JsonHttpService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public HttpResponse<InputStream> sendHttpGetRequest(URI requestUrl) throws IOException, InterruptedException {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public <T> T parseJsonResponse(InputStream responseBody, Type targetType) {
		throw new UnsupportedOperationException("Not Implemented");
	}
}
