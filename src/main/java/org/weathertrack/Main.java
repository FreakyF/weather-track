package org.weathertrack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.weathertrack.api.service.ApiServiceModule;
import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.api.service.forecast.ForecastApiModule;
import org.weathertrack.api.service.forecast.ForecastApiService;
import org.weathertrack.api.service.forecast.openmeteo.resource.ForecastInterpreter;
import org.weathertrack.api.service.forecast.openmeteo.resource.OpenMeteoForecastApiServiceResource;
import org.weathertrack.api.service.geocoding.GeocodingApiModule;
import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.api.service.resource.ApiUriDataResource;
import org.weathertrack.input.service.userio.UserIOModule;
import org.weathertrack.input.service.userio.UserIOService;
import org.weathertrack.logging.LoggingModule;
import org.weathertrack.logging.factory.LoggerFactory;

import java.io.IOException;

@SuppressWarnings("java:S106")
public class Main {
	private static final Injector injector = Guice.createInjector(
			new LoggingModule(),
			new UserIOModule(),
			new ApiServiceModule(),
			new GeocodingApiModule(),
			new ForecastApiModule()
	);

	public static void main(String[] args) throws BadRequestException, NotFoundException, IOException, InterruptedException {
		ForecastInterpreter.initialize(injector.getInstance(LoggerFactory.class));
		ApiUriDataResource.initialize(injector.getInstance(LoggerFactory.class));
		OpenMeteoForecastApiServiceResource.initialize(injector.getInstance(LoggerFactory.class));

		System.out.println("===================================");
		System.out.println("Welcome to WeatherTrack!");
		System.out.println("===================================");
		var userIOService = injector.getInstance(UserIOService.class);
		var cityName = userIOService.getCityNameFromUser();
		var geocodingApiService = injector.getInstance(GeocodingApiService.class);
		var getCitiesForCityNameResponse = geocodingApiService.fetchCitiesForCityName(cityName);
		if (getCitiesForCityNameResponse.success()) {
			userIOService.printCitiesWithSameName(getCitiesForCityNameResponse.value());
			var userChoice = userIOService.getUserInput("Please enter the index: ");
			var selectedCity = getCitiesForCityNameResponse.value().get(Integer.parseInt(userChoice) - 1);
			var forecastApiService = injector.getInstance(ForecastApiService.class);
			var getForecastForCoordinatesResponse = forecastApiService.fetchForecastForCoordinates(selectedCity);
			if (getForecastForCoordinatesResponse.success()) {
				userIOService.printWeather(getForecastForCoordinatesResponse.value());
			}

			return;
		}

		var logger = injector.getInstance(org.weathertrack.logging.Logger.class);
		logger.error(getCitiesForCityNameResponse.message());
	}
}
