package org.weathertrack.api.service.geocoding.openmeteo;

import org.apache.http.client.utils.URIBuilder;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataResponseDTO;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.http.json.JsonHttpService;
import org.weathertrack.geocoding.model.GeocodingCityData;
import org.weathertrack.geocoding.model.GeocodingData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpenMeteoGeocodingApiService implements GeocodingApiService {
	private static final String GEOCODING_API_SCHEME = "https";
	private static final String GEOCODING_API_HOST = "geocoding-api.open-meteo.com";
	private static final String GEOCODING_API_PATH = "/v1/search";

	HttpClient httpClient = HttpClient.newHttpClient();
	private final HttpService httpService = new JsonHttpService(httpClient);

	@Override
	public List<GeocodingCityData> fetchCitiesForCityName(String cityName) {
		validateCityName(cityName);
		var requestUrl = buildGeocodingApiUri(cityName);

		var response = fetchGeocodingCityDataFromApi(requestUrl);
		if (response == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL);
		}

		return response;
	}

	public List<GeocodingCityData> fetchGeocodingCityDataFromApi(URI requestUrl) {
		try {
			var response = httpService.sendHttpGetRequest(requestUrl);
			if (checkApiStatus(response)) {
				return parseCityDataFromResponse(response.body());
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private List<GeocodingCityData> parseCityDataFromResponse(InputStream response) {
		CityDataResponseDTO responseJson = httpService.parseJsonResponse(response, CityDataResponseDTO.class);

		if (responseJson != null && responseJson.getResults() != null) {
			List<GeocodingCityData> geocodingCityDataList = new ArrayList<>();
			convertToGeocodingCityData(responseJson, geocodingCityDataList);
			return geocodingCityDataList;
		} else {
			return new ArrayList<>();
		}
	}

	private static void convertToGeocodingCityData(CityDataResponseDTO responseJson, List<GeocodingCityData> geocodingCityDataList) {
		for (CityDataDTO cityDataDTO : responseJson.getResults()) {
			var name = cityDataDTO.getName();
			var administration = cityDataDTO.getAdmin1();
			var country = cityDataDTO.getCountry();
			GeocodingCityData geocodingCityData = new GeocodingCityData(name, administration, country);
			geocodingCityDataList.add(geocodingCityData);

		}
	}

	private static boolean checkApiStatus(HttpResponse<InputStream> response) {
		if (response.statusCode() == 200) {
			return true;
		}
		if (response.statusCode() == 500) {
			throw new UnsupportedOperationException("Not Implemented");
		}
		if (response.statusCode() == 404) {
			throw new UnsupportedOperationException("Not Implemented");
		}
		return false;
	}

	private URI buildGeocodingApiUri(String cityName) {
		try {
			return new URIBuilder()
					.setScheme(GEOCODING_API_SCHEME)
					.setHost(GEOCODING_API_HOST)
					.setPath(GEOCODING_API_PATH)
					.setParameter("name", cityName)
					.build();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private void validateCityName(String cityName) {
		if (cityName == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.CITY_NAME_IS_NULL);
		}
		if (cityName.isBlank()) {
			throw new IllegalArgumentException(ApiServiceExceptionMessage.CITY_NAME_IS_BLANK);
		}
	}

	@Override
	public GeocodingData fetchGeocodingDataForCity(GeocodingCityData geocodingCityData) {
		throw new UnsupportedOperationException("Not Implemented");
	}
}
