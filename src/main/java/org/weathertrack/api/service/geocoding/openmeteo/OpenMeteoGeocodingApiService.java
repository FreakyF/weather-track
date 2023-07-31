package org.weathertrack.api.service.geocoding.openmeteo;

import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.geocoding.model.GeocodingCityData;
import org.weathertrack.geocoding.model.GeocodingData;

import java.net.URI;
import java.util.List;

public class OpenMeteoGeocodingApiService implements GeocodingApiService {
	private static final String GEOCODING_API_SCHEME = "https";
	private static final String GEOCODING_API_HOST = "geocoding-api.open-meteo.com";
	private static final String GEOCODING_API_PATH = "/v1/search";

	@Override
	public List<GeocodingCityData> fetchCitiesForCityName(String cityName) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	public List<GeocodingCityData> fetchGeocodingCityDataFromApi(URI requestUrl) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	public URI buildGeocodingApiUri(String cityName) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	public void validateCityName(String cityName) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public GeocodingData fetchGeocodingDataForCity(GeocodingCityData geocodingCityData) {
		throw new UnsupportedOperationException("Not Implemented");
	}
}
