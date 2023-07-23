package org.weathertrack.weather;

public interface WeatherDataProvider {
	WeatherData getWeatherData(String city);
}
