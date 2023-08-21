package org.weathertrack.api.service.forecast;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.hc.core5.net.URIBuilder;

import static org.weathertrack.api.service.resource.ApiUriDataResource.getApiUriDataFromProperties;

public class ForecastApiModule extends AbstractModule {
	public static final String ANNOTATION_FORECAST_API = "ANNOTATION_FORECAST_API";

	private static final String FORECAST_API_SCHEME_PROPERTY_NAME = "FORECAST_API_SCHEME";
	private static final String FORECAST_API_HOST_PROPERTY_NAME = "FORECAST_API_HOST";
	private static final String FORECAST_API_PATH_PROPERTY_NAME = "FORECAST_API_PATH";

	@Override
	protected void configure() {
		configureForecastApiUri();
	}

	private void configureForecastApiUri() {
		var forecastApiUriData = getApiUriDataFromProperties(FORECAST_API_SCHEME_PROPERTY_NAME, FORECAST_API_HOST_PROPERTY_NAME, FORECAST_API_PATH_PROPERTY_NAME);
		var forecastApiServiceUriBuilder = new URIBuilder()
				.setScheme(forecastApiUriData.get(FORECAST_API_SCHEME_PROPERTY_NAME))
				.setHost(forecastApiUriData.get(FORECAST_API_HOST_PROPERTY_NAME))
				.setPath(forecastApiUriData.get(FORECAST_API_PATH_PROPERTY_NAME));
		bind(URIBuilder.class).annotatedWith(Names.named(ANNOTATION_FORECAST_API)).toInstance(forecastApiServiceUriBuilder);
	}
}
