package org.weathertrack.input.service;

import org.weathertrack.weather.model.WeatherData;

import java.util.List;

public interface InputService {
	String getUserInput(String message);

	String getCityNameFromUser();

	void printCitiesWithSameName(List<String> citiesWithSameName);

	void printWeather(WeatherData weatherData);
}
