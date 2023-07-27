package org.weathertrack.weather.provider;

import org.weathertrack.weather.model.WeatherData;
import org.weathertrack.weather.provider.openmeteo.model.CityData;

public interface WeatherDataProvider {
	WeatherData getWeatherData(CityData city);
}
