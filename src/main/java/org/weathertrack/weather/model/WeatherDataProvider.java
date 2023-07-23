package org.weathertrack.weather.model;

public interface WeatherDataProvider {
	WeatherData getWeatherData(String city);
}
