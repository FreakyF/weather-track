package org.weathertrack.weather.provider.openmeteo.geocoding;

import org.apache.http.client.utils.URIBuilder;
import org.weathertrack.weather.provider.HttpJsonHandler;
import org.weathertrack.weather.provider.openmeteo.model.city.CityData;
import org.weathertrack.weather.provider.openmeteo.model.city.CityDataResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OpenMeteoGeocodingProvider implements GeocodingProvider {
	private static final String GEOCODING_API_SCHEME = "https";
	private static final String GEOCODING_API_HOST = "geocoding-api.open-meteo.com";
	private static final String GEOCODING_API_PATH = "/v1/search";
	HttpJsonHandler httpJsonHandler = new HttpJsonHandler();

	@Override
	public List<CityData> fetchCityDataFromCityName(String cityName) {
		URIBuilder uriBuilder = new URIBuilder()
				.setScheme(GEOCODING_API_SCHEME)
				.setHost(GEOCODING_API_HOST)
				.setPath(GEOCODING_API_PATH)
				.setParameter("name", cityName);

		try {
			var requestUrl = uriBuilder.build();
			var response = fetchCityDataFromAPI(requestUrl);
			if (response != null) {
				return response;
			}
		} catch (URISyntaxException | InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private List<CityData> fetchCityDataFromAPI(URI requestUrl) throws InterruptedException {
		try {
			var response = httpJsonHandler.sendHttpGetRequest(requestUrl);
			// TODO: Handle all status code responses;
			if (response.statusCode() == 200) {
				return parseCityDataFromResponse(response.body());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private List<CityData> parseCityDataFromResponse(InputStream response) {
		CityDataResponse responseJson = httpJsonHandler.parseJsonResponse(response, CityDataResponse.class);

		if (responseJson != null && responseJson.getResults() != null) {
			return Arrays.asList(responseJson.getResults());
		} else {
			return new ArrayList<>();
		}
	}
}
