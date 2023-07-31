package org.weathertrack.api.service.geocoding;

import org.weathertrack.geocoding.model.GeocodingCityData;
import org.weathertrack.geocoding.model.GeocodingData;

import java.util.List;

public interface GeocodingApiService {
	List<GeocodingCityData> fetchCitiesForCityName(String cityName);

	GeocodingData fetchGeocodingDataForCity(GeocodingCityData geocodingCityData);
}
