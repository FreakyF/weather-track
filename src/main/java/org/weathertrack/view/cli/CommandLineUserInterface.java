package org.weathertrack.view.cli;

import org.weathertrack.model.WeatherData;
import org.weathertrack.service.input.UserInputService;
import org.weathertrack.view.UserInterface;
import org.weathertrack.view.util.WeatherUIResources;

import java.util.List;

@SuppressWarnings("java:S106")
public class CommandLineUserInterface implements UserInterface {
	private final UserInputService userInputService;

	public CommandLineUserInterface(UserInputService userInputService) {
		this.userInputService = userInputService;
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
		var weatherConditionMessage = String.format(
				WeatherUIResources.WEATHER_CONDITION,
				weatherData.getWeatherCondition()
		);
		System.out.println(weatherConditionMessage);

		var temperatureMessage = String.format(
				WeatherUIResources.TEMPERATURE,
				weatherData.getTemperature()
		);
		System.out.println(temperatureMessage);

		var cloudinessMessage = String.format(
				WeatherUIResources.CLOUDINESS,
				weatherData.getCloudiness()
		);
		System.out.println(cloudinessMessage);

		var rainChanceMessage = String.format(
				WeatherUIResources.RAIN_CHANCE,
				weatherData.getRainChance()
		);

		System.out.println(rainChanceMessage);

		var windSpeedMessage = String.format(
				WeatherUIResources.WIND_SPEED,
				weatherData.getWindSpeed()
		);
		System.out.println(windSpeedMessage);

		var humidityMessage = String.format(
				WeatherUIResources.HUMIDITY,
				weatherData.getHumidity()
		);
		System.out.println(humidityMessage);

		var pressureMessage = String.format(
				WeatherUIResources.PRESSURE,
				weatherData.getPressure()
		);
		System.out.println(pressureMessage);
	}
}
