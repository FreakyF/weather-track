package org.weathertrack.view.cli;

import org.weathertrack.model.WeatherData;
import org.weathertrack.view.UserInterface;
import org.weathertrack.view.util.WeatherUIResources;

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
	public void printCitiesWithSameName(String cityName, List<String> citiesWithSameName) {
		System.out.println("Cities with the name " + cityName + ":");

		if (citiesWithSameName.isEmpty()) {
			System.out.println("No cities found with the name " + cityName + ".");
		} else {
			StringBuilder message = new StringBuilder();
			for (int i = 0; i < citiesWithSameName.size(); i++) {
				message.append(i).append(". ").append(citiesWithSameName.get(i));
				if (i != citiesWithSameName.size() - 1) {
					message.append("\n");
				}
			}
			System.out.println(message);
		}
	}

	@Override
	public void printWeather(WeatherData weatherData) {
		System.out.println((WeatherUIResources.WEATHER_CONDITION) + " " + weatherData.getWeatherCondition());
		System.out.println((WeatherUIResources.TEMPERATURE) + " " + weatherData.getTemperature());
		System.out.println((WeatherUIResources.CLOUDINESS) + " " + weatherData.getCloudiness());
		System.out.println((WeatherUIResources.RAIN_CHANCE) + " " + weatherData.getRainChance());
		System.out.println((WeatherUIResources.WIND_SPEED) + " " + weatherData.getWindSpeed());
		System.out.println((WeatherUIResources.HUMIDITY) + " " + weatherData.getHumidity());
		System.out.println((WeatherUIResources.PRESSURE) + " " + weatherData.getPressure());
	}
}
