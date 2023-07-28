package org.weathertrack.api.weather;

import org.weathertrack.api.geocoding.openmeteo.model.city.CityData;
import org.weathertrack.api.weather.model.WeatherData;

public interface WeatherDataProvider {
	WeatherData getWeatherData(CityData city);
}
