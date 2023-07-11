package org.weathertrack.view;


import org.weathertrack.view.cli.UserInterfaceCLI;

public class Main {
	public static void main(String[] args) {
		UserInterfaceCLI userInterfaceCLI = new UserInterfaceCLI();

		String cityName = userInterfaceCLI.getCityNameFromUser();

		userInterfaceCLI.printCitiesWithSameName(cityName);

		userInterfaceCLI.printWeather(cityName);
	}
}
