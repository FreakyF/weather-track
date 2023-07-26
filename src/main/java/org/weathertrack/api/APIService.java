package org.weathertrack.api;

import org.weathertrack.api.model.Coordinates;
import org.weathertrack.weather.model.WeatherData;

public interface APIService {
	WeatherData fetchWeatherFromCoordinates(Coordinates coordinates);

	Coordinates fetchCoordinatesFromCityName(String cityName);
}
