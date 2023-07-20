package org.weathertrack.view.cli;

import org.weathertrack.model.WeatherData;
import org.weathertrack.service.input.UserInputService;
import org.weathertrack.util.logger.LoggerInterface;
import org.weathertrack.view.UserInterface;
import org.weathertrack.view.util.LogMessages;
import org.weathertrack.view.util.WeatherUIResources;

import java.util.List;

@SuppressWarnings("java:S106")
public class CommandLineUserInterface implements UserInterface {
	private final UserInputService userInputService;

	private final LoggerInterface<CommandLineUserInterface> logger;

	public CommandLineUserInterface(UserInputService userInputService, LoggerInterface<CommandLineUserInterface> logger) {
		this.userInputService = userInputService;
		this.logger = logger;
	}

	@Override
	public String getCityNameFromUser() {
		String cityName;
		do {
			cityName = userInputService.getUserInput("Enter the city name: ");
		} while (cityName.isBlank());

		return cityName;
	}

	@Override
	public void printCitiesWithSameName(List<String> citiesWithSameName) {
		if (citiesWithSameName.isEmpty()) {
			logger.warn(LogMessages.CITIES_WITH_SAME_NAME_IS_EMPTY);
			return;
		}

		StringBuilder message = new StringBuilder();
		for (int i = 0; i < citiesWithSameName.size(); i++) {
			message.append(i + 1).append(". ").append(citiesWithSameName.get(i));
			var isLastCity = i == citiesWithSameName.size() - 1;
			if (!isLastCity) {
				message.append("\n");
			}
		}
		System.out.print(message);
	}

	@Override
	public void printWeather(WeatherData weatherData) {
		var weatherConditionMessage = String.format(
				WeatherUIResources.WEATHER_CONDITION,
				weatherData.weatherCondition()
		);
		System.out.println(weatherConditionMessage);

		var temperatureMessage = String.format(
				WeatherUIResources.TEMPERATURE,
				weatherData.temperatureCelsius()
		);
		System.out.println(temperatureMessage);

		var cloudinessMessage = String.format(
				WeatherUIResources.CLOUDINESS,
				weatherData.cloudiness()
		);
		System.out.println(cloudinessMessage);

		var rainChanceMessage = String.format(
				WeatherUIResources.RAIN_CHANCE,
				weatherData.rainChance()
		);

		System.out.println(rainChanceMessage);

		var windSpeedMessage = String.format(
				WeatherUIResources.WIND_SPEED,
				weatherData.windSpeed()
		);
		System.out.println(windSpeedMessage);

		var humidityMessage = String.format(
				WeatherUIResources.HUMIDITY,
				weatherData.humidity()
		);
		System.out.println(humidityMessage);

		var pressureMessage = String.format(
				WeatherUIResources.PRESSURE,
				weatherData.pressure()
		);
		System.out.println(pressureMessage);
	}
}
