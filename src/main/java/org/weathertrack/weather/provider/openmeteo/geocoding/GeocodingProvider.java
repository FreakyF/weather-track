package org.weathertrack.weather.provider.openmeteo.geocoding;

import org.weathertrack.weather.provider.openmeteo.model.CityData;

import java.util.List;

public interface GeocodingProvider {
	List<CityData> fetchCityDataFromCityName(String cityName);
}
