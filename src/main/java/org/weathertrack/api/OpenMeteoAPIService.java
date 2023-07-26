package org.weathertrack.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.weathertrack.api.model.Coordinates;
import org.weathertrack.weather.model.WeatherData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class OpenMeteoAPIService implements APIService {
	private static final String GEOCODING_API_ENDPOINT = "https://geocoding-api.open-meteo.com/v1/search";
	private static final String RESULTS_KEY = "results";

	@Override
	public WeatherData fetchWeatherFromCoordinates(Coordinates coordinates) {
		return null;
	}

	@Override
	public Coordinates fetchCoordinatesFromCityName(String cityName) {
		String requestUrl = GEOCODING_API_ENDPOINT + "?name=" + cityName;
		try {
			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest httpRequest = HttpRequest.newBuilder()
					.uri(URI.create(requestUrl))
					.GET()
					.build();

			HttpResponse<InputStream> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
			if (response.statusCode() == 200) {
				Optional<Coordinates> coordinates = parseCoordinatesFromResponse(response.body());
				return coordinates.orElse(null);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Optional<Coordinates> parseCoordinatesFromResponse(InputStream responseBody) {
		try (InputStreamReader inputStreamReader = new InputStreamReader(responseBody)) {
			Gson gson = new Gson();
			JsonObject responseJson = gson.fromJson(inputStreamReader, JsonObject.class);

			if (responseJson.has(RESULTS_KEY) && responseJson.get(RESULTS_KEY).isJsonArray()) {
				JsonArray resultsArray = responseJson.getAsJsonArray(RESULTS_KEY);
				if (resultsArray.size() > 0) {
					JsonElement firstResult = resultsArray.get(0);
					if (firstResult.isJsonObject()) {
						JsonObject firstResultObj = firstResult.getAsJsonObject();
						double latitude = firstResultObj.get("latitude").getAsDouble();
						double longitude = firstResultObj.get("longitude").getAsDouble();
						return Optional.of(new Coordinates(latitude, longitude));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
