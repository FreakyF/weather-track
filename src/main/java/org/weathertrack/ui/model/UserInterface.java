package org.weathertrack.ui.model;

import org.weathertrack.weather.model.WeatherData;

import java.util.List;

public interface UserInterface {
	String getCityNameFromUser();

	void printCitiesWithSameName(List<String> citiesWithSameName);

	void printWeather(WeatherData weatherData);
}
