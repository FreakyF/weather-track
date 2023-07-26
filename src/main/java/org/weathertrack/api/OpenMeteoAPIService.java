package org.weathertrack.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.weathertrack.api.model.Coordinates;
import org.weathertrack.weather.model.WeatherData;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenMeteoAPIService implements APIService {
	private static final String GEOCODING_API_ENDPOINT = "https://geocoding-api.open-meteo.com/v1/search";
	private static final String WEATHER_API_ENDPOINT = "https://geocoding-api.open-meteo.com/v1/forecast";

	@Override
	public WeatherData fetchWeatherFromCoordinates(Coordinates coordinates) {
		// Implement weather data retrieval from the weather API here.
		return null;
	}

	@Override
	public Coordinates fetchCoordinatesFromCityName(String cityName) {
		String requestUrl = GEOCODING_API_ENDPOINT + "?name=" + cityName;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Gson gson = new Gson();
				InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
				JsonObject responseJson = gson.fromJson(inputStreamReader, JsonObject.class);

				if (responseJson.has("results") && responseJson.get("results").isJsonArray()) {
					JsonArray resultsArray = responseJson.getAsJsonArray("results");
					if (resultsArray.size() > 0) {
						JsonElement firstResult = resultsArray.get(0);
						if (firstResult.isJsonObject()) {
							JsonObject firstResultObj = firstResult.getAsJsonObject();
							double latitude = firstResultObj.get("latitude").getAsDouble();
							double longitude = firstResultObj.get("longitude").getAsDouble();
							return new Coordinates(latitude, longitude);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
