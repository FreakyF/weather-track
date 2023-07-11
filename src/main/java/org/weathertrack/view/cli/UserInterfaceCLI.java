package org.weathertrack.view.cli;

import org.weathertrack.view.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterfaceCLI implements UserInterface {
	private final Scanner scanner;

	public UserInterfaceCLI() {
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
	public void printWeather(String cityName) {
		String temperature = "0";
		String cloudiness = "0";
		String rainChance = "0";
		String windSpeed = "0";
		String weatherCondition = "0";
		String humidity = "0";
		String pressure = "0";

		System.out.println("\nWeather Information for " + cityName + ":");
		System.out.println("Weather Condition: " + weatherCondition);
		System.out.println("Temperature: " + temperature + "°C");
		System.out.println("Cloudiness: " + cloudiness + "%");
		System.out.println("Rain Chance: " + rainChance + "%");
		System.out.println("Wind Speed: " + windSpeed + " m/s");
		System.out.println("Humidity: " + humidity + "%");
		System.out.println("Pressure: " + pressure + " hPa");
	}
}
