package org.weathertrack.weather.provider;

import org.weathertrack.weather.model.WeatherData;

public interface WeatherDataProvider {
	WeatherData getWeatherData(String city);
}
