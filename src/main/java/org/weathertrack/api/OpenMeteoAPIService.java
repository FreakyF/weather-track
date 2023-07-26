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

import static org.weathertrack.api.model.WeatherInterpretation.interpretWeatherCode;

public class OpenMeteoAPIService implements APIService {
	private static final String GEOCODING_API_ENDPOINT = "https://geocoding-api.open-meteo.com/v1/search";
	private static final String WEATHER_API_ENDPOINT = "https://api.open-meteo.com/v1/forecast";
	private static final String RESULTS_KEY = "results";
	private static final String HOURLY_KEY = "hourly";

	@Override
	public WeatherData fetchWeatherFromCoordinates(Coordinates coordinates) {
		String requestUrl = WEATHER_API_ENDPOINT +
				"?latitude=" + coordinates.latitude() +
				"&longitude=" + coordinates.longitude() +
				"&hourly=temperature_2m,relativehumidity_2m,rain,weathercode,surface_pressure,cloudcover,windspeed_10m";
		try {
			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest httpRequest = HttpRequest.newBuilder()
					.uri(URI.create(requestUrl))
					.GET()
					.build();

			HttpResponse<InputStream> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
			if (response.statusCode() == 200) {
				return parseWeatherDataFromResponse(response.body());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Coordinates fetchCoordinatesFromCityName(String cityName) {
		String requestUrl = GEOCODING_API_ENDPOINT +
				"?name=" +
				cityName;
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

	private WeatherData parseWeatherDataFromResponse(InputStream responseBody) {
		try (InputStreamReader inputStreamReader = new InputStreamReader(responseBody)) {
			Gson gson = new Gson();
			JsonObject responseJson = gson.fromJson(inputStreamReader, JsonObject.class);

			if (responseJson.has(HOURLY_KEY) && responseJson.get(HOURLY_KEY).isJsonObject()) {
				JsonObject hourlyData = responseJson.getAsJsonObject(HOURLY_KEY);
				JsonArray weatherCodeArray = hourlyData.getAsJsonArray("weathercode");
				JsonArray temperatureArray = hourlyData.getAsJsonArray("temperature_2m");
				JsonArray cloudinessArray = hourlyData.getAsJsonArray("cloudcover");
				JsonArray rainChanceArray = hourlyData.getAsJsonArray("rain");
				JsonArray windSpeedArray = hourlyData.getAsJsonArray("windspeed_10m");
				JsonArray humidityArray = hourlyData.getAsJsonArray("relativehumidity_2m");
				JsonArray pressureArray = hourlyData.getAsJsonArray("surface_pressure");

				if (temperatureArray.size() > 0) {
					int weatherCode = weatherCodeArray.get(0).getAsInt();
					String weatherCodeInterpreted = interpretWeatherCode(weatherCode);
					double temperature = temperatureArray.get(0).getAsDouble();
					int cloudiness = cloudinessArray.get(0).getAsInt();
					int rainChance = rainChanceArray.get(0).getAsInt();
					double windSpeed = windSpeedArray.get(0).getAsDouble();
					int humidity = humidityArray.get(0).getAsInt();
					int pressure = pressureArray.get(0).getAsInt();
					return new WeatherData(weatherCodeInterpreted, temperature, cloudiness, rainChance, windSpeed, humidity, pressure);
				}
			}
		} catch (IOException e) {
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
