package org.weathertrack.view.cli;

import org.weathertrack.model.WeatherData;
import org.weathertrack.view.UserInterface;
import org.weathertrack.view.util.WeatherUIResources;

import java.util.List;
import java.util.Scanner;

@SuppressWarnings("java:S106")
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
	public void printCitiesWithSameName(List<String> citiesWithSameName) {
		StringBuilder message = new StringBuilder();
		for (int i = 0; i < citiesWithSameName.size(); i++) {
			message.append(i).append(". ").append(citiesWithSameName.get(i));
			if (i != citiesWithSameName.size() - 1) {
				message.append("\n");
			}
		}
		System.out.println(message);
	}

	@Override
	public void printWeather(WeatherData weatherData) {
		var weatherCondition = String.format(WeatherUIResources.WEATHER_CONDITION, weatherData.getWeatherCondition());
		System.out.println(weatherCondition);

		var temperature = String.format(WeatherUIResources.TEMPERATURE, weatherData.getTemperature());
		System.out.println(temperature);

		var cloudiness = String.format(WeatherUIResources.CLOUDINESS, weatherData.getCloudiness());
		System.out.println(cloudiness);

		var rainChance = String.format(WeatherUIResources.RAIN_CHANCE, weatherData.getRainChance());
		System.out.println(rainChance);

		var windSpeed = String.format(WeatherUIResources.WIND_SPEED, weatherData.getWindSpeed());
		System.out.println(windSpeed);

		var humidity = String.format(WeatherUIResources.HUMIDITY, weatherData.getHumidity());
		System.out.println(humidity);

		var pressure = String.format(WeatherUIResources.PRESSURE, weatherData.getPressure());
		System.out.println(pressure);
	}
}
