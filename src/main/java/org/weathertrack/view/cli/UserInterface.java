package org.weathertrack.view.cli;

public interface UserInterface {
	String getCityNameFromUser();

	void printCitiesWithSameName();

	void printWeather();
}
