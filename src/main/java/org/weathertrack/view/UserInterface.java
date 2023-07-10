package org.weathertrack.view;

public interface UserInterface {
	String getCityNameFromUser();

	void printCitiesWithSameName(String cityName);

	void printWeather();
}
