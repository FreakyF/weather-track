package org.weathertrack.api.service.forecast;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.hc.core5.net.URIBuilder;

public class ForecastApiModule extends AbstractModule {
	public static final String ANNOTATION_FORECAST_API = "ANNOTATION_GEOCODING_API";
	private static final String GEOCODING_API_SCHEME = "https";
	private static final String GEOCODING_API_HOST = "https://api.open-meteo.com";
	private static final String GEOCODING_API_PATH = "/v1/forecast";

	@Override
	protected void configure() {
		var geocodingApiServiceUriBuilder = new URIBuilder()
				.setScheme(GEOCODING_API_SCHEME)
				.setHost(GEOCODING_API_HOST)
				.setPath(GEOCODING_API_PATH);
		bind(URIBuilder.class).annotatedWith(Names.named(ANNOTATION_FORECAST_API)).toInstance(geocodingApiServiceUriBuilder);
	}
}
