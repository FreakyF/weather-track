package org.weathertrack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.weathertrack.api.service.ApiServiceModule;
import org.weathertrack.api.service.forecast.ForecastApiModule;
import org.weathertrack.api.service.geocoding.GeocodingApiModule;
import org.weathertrack.input.service.userio.UserIOModule;
import org.weathertrack.logging.LoggingModule;

public class Main {
	private static final Injector injector = Guice.createInjector(
			new LoggingModule(),
			new UserIOModule(),
			new ApiServiceModule(),
			new GeocodingApiModule(),
			new ForecastApiModule()
	);

	public static void main(String[] args) {

	}
}
