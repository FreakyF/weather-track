package org.weathertrack.api.service.http;

import org.weathertrack.api.service.http.exception.ParseJsonException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpResponse;

public interface HttpService {
	HttpResponse<InputStream> sendHttpGetRequest(URI requestUrl) throws IOException, InterruptedException;

	/**
	 * @throws ParseJsonException when it could not parse Json response for the target type.
	 */
	<T> T parseJsonResponse(InputStream responseBody, Class<T> clazz) throws ParseJsonException;
}
