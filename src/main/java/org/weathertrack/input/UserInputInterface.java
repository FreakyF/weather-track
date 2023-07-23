package org.weathertrack.input;

import org.weathertrack.weather.model.WeatherData;

import java.util.List;

public interface UserInputInterface {
	String getCityNameFromUser();

	void printCitiesWithSameName(List<String> citiesWithSameName);

	void printWeather(WeatherData weatherData);
}
