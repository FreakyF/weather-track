package org.weathertrack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.weathertrack.api.service.ApiServiceModule;
import org.weathertrack.api.service.geocoding.GeocodingApiModule;
import org.weathertrack.forecast.ForecastModule;
import org.weathertrack.geocoding.GeocodingModule;
import org.weathertrack.input.service.userio.UserIOModule;
import org.weathertrack.logging.LoggingModule;

public class Main {
	private static final Injector injector = Guice.createInjector(
			new LoggingModule(),
			new UserIOModule(),
			new ApiServiceModule(),
			new GeocodingModule(),
			new GeocodingApiModule(),
			new ForecastModule()
	);

	public static void main(String[] args) {

	}
}
