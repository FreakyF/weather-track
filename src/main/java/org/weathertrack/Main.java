package org.weathertrack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.weathertrack.api.APIService;
import org.weathertrack.api.OpenMeteoAPIService;
import org.weathertrack.input.service.userio.CommandLineUserIOService;
import org.weathertrack.input.service.userio.UserIOModule;
import org.weathertrack.logging.LoggingModule;

public class Main {
	private static final Injector injector = Guice.createInjector(new LoggingModule(), new UserIOModule());

	public static void main(String[] args) {
		var userIOService = injector.getInstance(CommandLineUserIOService.class);
		var cityName = userIOService.getCityNameFromUser();
		APIService apiService = new OpenMeteoAPIService();
		var coordinatesFromCityName = apiService.fetchCoordinatesFromCityName(cityName);
		var weatherDataFromCoordinates = apiService.fetchWeatherFromCoordinates(coordinatesFromCityName);
		userIOService.printWeather(weatherDataFromCoordinates);
	}
}
