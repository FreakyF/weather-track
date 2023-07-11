package org.weathertrack.service;

import org.weathertrack.model.WeatherData;

public interface WeatherDataProvider {

	WeatherData getWeatherData(String city);
}
