package org.weathertrack.api.service.forecast;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ForecastApiModule extends AbstractModule {
	public static final String ANNOTATION_FORECAST_API = "ANNOTATION_FORECAST_API";

	private static final String PROPERTIES_FILE = "api/config.properties";
	private static final String FORECAST_API_SCHEME_PROPERTY_NAME = "FORECAST_API_SCHEME";
	private static final String FORECAST_API_HOST_PROPERTY_NAME = "FORECAST_API_HOST";
	private static final String FORECAST_API_PATH_PROPERTY_NAME = "FORECAST_API_PATH";

	@Override
	protected void configure() {
		configureForecastApiUri();
	}

	private void configureForecastApiUri() {
		var forecastApiUriData = getForecastApiUriDataFromProperties();
		var forecastApiServiceUriBuilder = new URIBuilder()
				.setScheme(forecastApiUriData.get(FORECAST_API_SCHEME_PROPERTY_NAME))
				.setHost(forecastApiUriData.get(FORECAST_API_HOST_PROPERTY_NAME))
				.setPath(FORECAST_API_PATH_PROPERTY_NAME);
		bind(URIBuilder.class).annotatedWith(Names.named(ANNOTATION_FORECAST_API)).toInstance(forecastApiServiceUriBuilder);
	}

	private static Map<String, String> getForecastApiUriDataFromProperties() {
		Map<String, String> forecastApiUriData = new HashMap<>();
		try (InputStream inputStream = ForecastApiModule.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
			var properties = new Properties();
			properties.load(inputStream);
			forecastApiUriData.put(FORECAST_API_SCHEME_PROPERTY_NAME, properties.getProperty(FORECAST_API_SCHEME_PROPERTY_NAME));
			forecastApiUriData.put(FORECAST_API_HOST_PROPERTY_NAME, properties.getProperty(FORECAST_API_HOST_PROPERTY_NAME));
			forecastApiUriData.put(FORECAST_API_PATH_PROPERTY_NAME, properties.getProperty(FORECAST_API_PATH_PROPERTY_NAME));
		} catch (IOException e) {
			e.printStackTrace(); // TODO: Improve exception handling. Throw appropiate exception. Handle how user should be notified.
		}
		return forecastApiUriData;
	}
}
