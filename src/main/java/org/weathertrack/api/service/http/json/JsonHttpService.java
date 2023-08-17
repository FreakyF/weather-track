package org.weathertrack.api.service.http.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
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
import java.time.LocalDateTime;

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

	public <T> T parseJsonResponse(InputStream responseBody, Type targetType) {
		try (InputStreamReader inputStreamReader = new InputStreamReader(responseBody)) {
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
					.create();

			return gson.fromJson(inputStreamReader, targetType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
		@Override
		public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}

		@Override
		public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return LocalDateTime.parse(json.getAsString());
		}
	}
}
