package org.weathertrack.api;

import org.weathertrack.api.geocoding.openmeteo.model.city.CityData;
import org.weathertrack.api.model.WeatherData;

public interface WeatherDataProvider {
	WeatherData getWeatherData(CityData city);
}
