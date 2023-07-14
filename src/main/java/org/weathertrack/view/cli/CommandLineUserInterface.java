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
		var weatherConditionMessage = String.format(WeatherUIResources.WEATHER_CONDITION, weatherData.getWeatherCondition());
		System.out.println(weatherConditionMessage);

		var temperatureMessage = String.format(WeatherUIResources.TEMPERATURE, weatherData.getTemperature());
		System.out.println(temperatureMessage);

		var cloudinessMessage = String.format(WeatherUIResources.CLOUDINESS, weatherData.getCloudiness());
		System.out.println(cloudinessMessage);

		var rainChanceMessage = String.format(WeatherUIResources.RAIN_CHANCE, weatherData.getRainChance());
		System.out.println(rainChanceMessage);

		var windSpeedMessage = String.format(WeatherUIResources.WIND_SPEED, weatherData.getWindSpeed());
		System.out.println(windSpeedMessage);

		var humidityMessage = String.format(WeatherUIResources.HUMIDITY, weatherData.getHumidity());
		System.out.println(humidityMessage);

		var pressureMessage = String.format(WeatherUIResources.PRESSURE, weatherData.getPressure());
		System.out.println(pressureMessage);
	}
}
