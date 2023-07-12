package org.weathertrack.view.cli;

import org.weathertrack.view.UserInterface;
import org.weathertrack.view.util.WeatherUIResources;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineUserInterface implements UserInterface {
	private final Scanner scanner;

	public CommandLineUserInterface() {
		this.scanner = new Scanner(System.in);
	}

	@Override
	public String getCityNameFromUser() {
		String cityName;
		do {
			System.out.print("Enter the city name: ");
			cityName = scanner.nextLine().trim();
		} while (cityName.isBlank());

		return cityName;
	}

	@Override
	public void printCitiesWithSameName(String cityName) {
		System.out.println("\nCities with the name " + cityName + ":");

		List<String> citiesWithSameName = new ArrayList<>();
		if (citiesWithSameName.isEmpty()) {
			System.out.println("No cities found with the name " + cityName + ".");
		} else {
			for (int i = 0; i < citiesWithSameName.size(); i++) {
				System.out.println(i + ". " + citiesWithSameName.get(i));
			}
		}

	}

	@Override
	public void printWeather() {

		System.out.println(WeatherUIResources.WEATHER_CONDITION);
		System.out.println(WeatherUIResources.TEMPERATURE);
		System.out.println(WeatherUIResources.CLOUDINESS);
		System.out.println(WeatherUIResources.RAIN_CHANCE);
		System.out.println(WeatherUIResources.WIND_SPEED);
		System.out.println(WeatherUIResources.HUMIDITY);
		System.out.println(WeatherUIResources.PRESSURE);
	}
}
