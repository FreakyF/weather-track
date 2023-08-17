package org.weathertrack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.weathertrack.api.service.ApiServiceModule;
import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.api.service.forecast.ForecastApiModule;
import org.weathertrack.api.service.forecast.ForecastApiService;
import org.weathertrack.api.service.geocoding.GeocodingApiModule;
import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.input.service.userio.UserIOModule;
import org.weathertrack.input.service.userio.UserIOService;
import org.weathertrack.logging.LoggingModule;

import java.io.IOException;

import static java.lang.System.out;

public class Main {
	private static final Injector injector = Guice.createInjector(
			new LoggingModule(),
			new UserIOModule(),
			new ApiServiceModule(),
			new GeocodingApiModule(),
			new ForecastApiModule()
	);

	public static void main(String[] args) throws BadRequestException, NotFoundException, IOException, InterruptedException {
		out.println("===================================");
		out.println("Welcome to WeatherTrack!");
		out.println("===================================");
		var userIOService = injector.getInstance(UserIOService.class);
		var cityName = "Brenna";
		var geocodingApiService = injector.getInstance(GeocodingApiService.class);
		var getCitiesForCityNameResponse = geocodingApiService.fetchCitiesForCityName(cityName);
		if (getCitiesForCityNameResponse.isSuccess()) {
			userIOService.printCitiesWithSameName(getCitiesForCityNameResponse.getValue());
			var userChoice = "1";
			var selectedCity = getCitiesForCityNameResponse.getValue().get(Integer.parseInt(userChoice) - 1);
			var forecastApiService = injector.getInstance(ForecastApiService.class);
			var getForecastForCoordinatesResponse = forecastApiService.fetchForecastForCoordinates(selectedCity);
			if (getForecastForCoordinatesResponse.isSuccess()) {
				userIOService.printWeather(getForecastForCoordinatesResponse.getValue());
			}

			return;
		}

		var logger = injector.getInstance(org.weathertrack.logging.Logger.class);
		logger.error(getCitiesForCityNameResponse.getMessage());
	}
}
