package org.weathertrack.api.service.forecast;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.hc.core5.net.URIBuilder;

public class ForecastApiModule extends AbstractModule {
	public static final String ANNOTATION_FORECAST_API = "ANNOTATION_FORECAST_API";
	private static final String FORECAST_API_SCHEME = "https";
	private static final String FORECAST_API_HOST = "api.open-meteo.com";
	private static final String FORECAST_API_PATH = "/v1/forecast";

	@Override
	protected void configure() {
		var forecastApiServiceUriBuilder = new URIBuilder()
				.setScheme(FORECAST_API_SCHEME)
				.setHost(FORECAST_API_HOST)
				.setPath(FORECAST_API_PATH);
		bind(URIBuilder.class).annotatedWith(Names.named(ANNOTATION_FORECAST_API)).toInstance(forecastApiServiceUriBuilder);
	}
}
