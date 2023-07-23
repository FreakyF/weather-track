package org.weathertrack.ui.view.cli;

import org.weathertrack.input.model.UserInputService;
import org.weathertrack.logger.model.Logger;
import org.weathertrack.ui.model.ExceptionMessage;
import org.weathertrack.ui.model.UserInterface;
import org.weathertrack.ui.model.WeatherUIResources;
import org.weathertrack.weather.model.WeatherData;

import java.util.List;

@SuppressWarnings("java:S106")
public class CommandLineUserInterface implements UserInterface {
	private final UserInputService userInputService;

	private final Logger<CommandLineUserInterface> logger;

	public CommandLineUserInterface(UserInputService userInputService, Logger<CommandLineUserInterface> logger) {
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
			logger.warn(ExceptionMessage.CITIES_WITH_SAME_NAME_IS_EMPTY);
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
