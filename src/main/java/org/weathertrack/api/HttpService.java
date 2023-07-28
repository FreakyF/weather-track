package org.weathertrack.api;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpResponse;

public interface HttpService {
	HttpResponse<InputStream> sendHttpGetRequest(URI requestUrl) throws IOException, InterruptedException;

	<T> T parseJsonResponse(InputStream responseBody, Type targetType);
}
