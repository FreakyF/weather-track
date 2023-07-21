package org.weathertrack.view;

import org.weathertrack.model.WeatherData;

import java.util.List;

public interface UserInterface {
	String getCityNameFromUser();

	void printCitiesWithSameName(List<String> citiesWithSameName);

	void printWeather(WeatherData weatherData);
}
