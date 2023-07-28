package org.weathertrack.api.geocoding;

import org.weathertrack.api.geocoding.openmeteo.model.city.CityData;

import java.util.List;

public interface GeocodingProvider {
	List<CityData> fetchCityDataFromCityName(String cityName);
}
