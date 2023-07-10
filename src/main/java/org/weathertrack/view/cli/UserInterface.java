package org.weathertrack.view.cli;

public interface UserInterface {
	String getCityNameFromUser();

	void printCitiesWithSameName(String cityName);

	void printWeather();
}
