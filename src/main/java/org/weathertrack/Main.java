package org.weathertrack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.weathertrack.api.geocoding.GeocodingModule;
import org.weathertrack.api.geocoding.openmeteo.OpenMeteoGeocodingProvider;
import org.weathertrack.api.geocoding.openmeteo.model.city.CityData;
import org.weathertrack.input.service.userio.CommandLineUserIOService;
import org.weathertrack.input.service.userio.UserIOModule;
import org.weathertrack.logging.LoggingModule;

import java.util.List;

public class Main {
	private static final Injector injectorUserIOService = Guice.createInjector(new LoggingModule(), new UserIOModule());
	private static final Injector injectorGeocodingProvider = Guice.createInjector(new LoggingModule(), new GeocodingModule());

	public static void main(String[] args) {
		var userIOService = injectorUserIOService.getInstance(CommandLineUserIOService.class);
		var cityName = userIOService.getCityNameFromUser();

		var geocodingProvider = injectorGeocodingProvider.getInstance(OpenMeteoGeocodingProvider.class);
		List<CityData> cityDataList = geocodingProvider.fetchCityData(cityName);

		userIOService.printCitiesWithSameName(cityDataList);
	}
}
