package org.weathertrack.view;

import org.weathertrack.model.WeatherData;

public interface UserInterface {
	String getCityNameFromUser();

	void printCitiesWithSameName(String cityName);

	void printWeather(WeatherData weatherData);
}
