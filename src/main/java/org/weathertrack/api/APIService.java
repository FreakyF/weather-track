package org.weathertrack.api;

import org.weathertrack.api.model.Coordinates;
import org.weathertrack.weather.model.WeatherData;

public interface APIService {
	// model
	WeatherData fetchWeatherFromCoordinates(Coordinates coordinates);

	// weatherForecast
	Coordinates fetchCoordinatesFromCityName(String cityName);
}
