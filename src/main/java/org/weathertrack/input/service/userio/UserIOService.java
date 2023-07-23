package org.weathertrack.input.service.userio;

import org.weathertrack.weather.model.WeatherData;

import java.util.List;

public interface UserIOService {
	String getUserInput(String message);

	String getCityNameFromUser();

	void printCitiesWithSameName(List<String> citiesWithSameName);

	void printWeather(WeatherData weatherData);
}
