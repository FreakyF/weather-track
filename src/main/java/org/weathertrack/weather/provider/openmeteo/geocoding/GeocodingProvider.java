package org.weathertrack.weather.provider.openmeteo.geocoding;

import org.weathertrack.weather.provider.openmeteo.model.CityData;

public interface GeocodingProvider {
	CityData fetchCityDataFromCityName(String cityName);
}
