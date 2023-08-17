package org.weathertrack.input.service.userio;

import com.google.inject.Inject;
import org.weathertrack.api.service.forecast.model.ForecastData;
import org.weathertrack.api.service.forecast.model.Unit;
import org.weathertrack.api.service.forecast.model.WeatherRecord;
import org.weathertrack.api.service.forecast.openmeteo.resource.ForecastInterpreter;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.input.resource.InputLogMessage;
import org.weathertrack.logging.Logger;
import org.weathertrack.logging.factory.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("java:S106")
public class CommandLineUserIOService implements UserIOService {
	private final Scanner scanner;
	private final Logger<CommandLineUserIOService> logger;

	@Inject
	public CommandLineUserIOService(LoggerFactory loggerFactory, Scanner scanner) {
		this.logger = loggerFactory.create(CommandLineUserIOService.class);
		this.scanner = scanner;
	}

	@Override
	public String getUserInput(String message) {
		System.out.print(message);
		return scanner.nextLine().trim();
	}

	@Override
	public String getCityNameFromUser() {
		String cityName;
		do {
			cityName = getUserInput("Enter the city name: ");
		} while (cityName.isBlank());

		return cityName;
	}

	@Override
	public void printCitiesWithSameName(List<GeocodingCityData> citiesWithSameName) {
		if (citiesWithSameName.isEmpty()) {
			logger.warn(InputLogMessage.CITIES_WITH_SAME_NAME_IS_EMPTY);
			return;
		}

		StringBuilder message = new StringBuilder();
		for (int i = 0; i < citiesWithSameName.size(); i++) {
			message.append(i + 1)
					.append(". ")
					.append(citiesWithSameName.get(i).name())
					.append(", ")
					.append(citiesWithSameName.get(i).administration())
					.append(", ")
					.append(citiesWithSameName.get(i).country())
					.append("\n");
		}
		System.out.print(message);
	}

	@Override
	public void printWeather(ForecastData forecastData) {
		System.out.println("\nWeather forecast for timezone: " + forecastData.getZoneId() + " (UTC offset: " + forecastData.getUtcOffsetSeconds() + " seconds)");

		System.out.println("\nHourly forecast:");
		System.out.println("------------------------------");
		for (Map.Entry<LocalDateTime, WeatherRecord> entry : forecastData.getHourlyWeatherRecords().entrySet()) {
			System.out.println("Time: " + entry.getKey());
			System.out.println("Temperature: " + entry.getValue().temperature() + " " + forecastData.getUnits().get(Unit.CELSIUS));
			System.out.println("Weather code: " + ForecastInterpreter.interpretWeatherCode(entry.getValue().weatherCode()));
			System.out.println("Wind speed: " + entry.getValue().windSpeed() + " " + forecastData.getUnits().get(Unit.KPH));
			System.out.println("Precipitation: " + entry.getValue().precipitation() + " " + forecastData.getUnits().get(Unit.MM));
			System.out.println("Humidity: " + (entry.getValue().humidity() != null ? entry.getValue().humidity() : "N/A") + " " + forecastData.getUnits().get(Unit.PERCENT));
			System.out.println("------------------------------");
		}

		System.out.println("\nDaily forecast:");
		System.out.println("------------------------------");
		for (Map.Entry<LocalDateTime, WeatherRecord> entry : forecastData.getDailyWeatherRecords().entrySet()) {
			System.out.println("Date: " + entry.getKey().toLocalDate());
			System.out.println("Max temperature: " + entry.getValue().temperature() + " " + forecastData.getUnits().get(Unit.CELSIUS));
			System.out.println("Weather code: " + ForecastInterpreter.interpretWeatherCode(entry.getValue().weatherCode()));
			System.out.println("Max wind speed: " + entry.getValue().windSpeed() + " " + forecastData.getUnits().get(Unit.KPH));
			System.out.println("Precipitation probability: " + entry.getValue().precipitation() + " " + forecastData.getUnits().get(Unit.PERCENT));
			System.out.println("------------------------------");
		}
	}
}
