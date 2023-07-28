package org.weathertrack.api.geocoding.openmeteo;

import org.apache.http.client.utils.URIBuilder;
import org.weathertrack.api.JsonHttpService;
import org.weathertrack.api.geocoding.GeocodingProvider;
import org.weathertrack.api.geocoding.openmeteo.model.city.CityData;
import org.weathertrack.api.geocoding.openmeteo.model.city.CityDataResponse;
import org.weathertrack.api.geocoding.openmeteo.resource.OpenMeteoExceptionMessage;

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
	JsonHttpService jsonHttpService = new JsonHttpService();

	@Override
	public List<CityData> fetchCityData(String cityName) {
		if (cityName == null || cityName.isBlank()) {
			throw new IllegalStateException(cityName == null
					? OpenMeteoExceptionMessage.CITY_NAME_IS_NULL
					: OpenMeteoExceptionMessage.CITY_NAME_IS_BLANK);
		}

		var uriBuilder = new URIBuilder()
				.setScheme(GEOCODING_API_SCHEME)
				.setHost(GEOCODING_API_HOST)
				.setPath(GEOCODING_API_PATH)
				.setParameter("name", cityName);

		try {
			var requestUrl = uriBuilder.build();
			var response = fetchCityDataFromAPI(requestUrl);
			if (response == null) {
				throw new NullPointerException(OpenMeteoExceptionMessage.CITY_DATA_IS_NULL);
			}
			return response;
		} catch (URISyntaxException | InterruptedException e) {
			e.printStackTrace();
		}
		throw new NullPointerException("XD");
	}

	private List<CityData> fetchCityDataFromAPI(URI requestUrl) throws InterruptedException {
		try {
			var response = jsonHttpService.sendHttpGetRequest(requestUrl);
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
		CityDataResponse responseJson = jsonHttpService.parseJsonResponse(response, CityDataResponse.class);

		if (responseJson != null && responseJson.getResults() != null) {
			return Arrays.asList(responseJson.getResults());
		} else {
			return new ArrayList<>();
		}
	}
}
