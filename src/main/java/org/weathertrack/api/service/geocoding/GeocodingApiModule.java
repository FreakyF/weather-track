package org.weathertrack.api.service.geocoding;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.http.client.utils.URIBuilder;

public class GeocodingApiModule extends AbstractModule {
	public static final String ANNOTATION_GEOCODING_API = "ANNOTATION_GEOCODING_API";
	private static final String GEOCODING_API_SCHEME = "https";
	private static final String GEOCODING_API_HOST = "geocoding-api.open-meteo.com";
	private static final String GEOCODING_API_PATH = "/v1/search";

	@Override
	protected void configure() {
		var geocodingApiServiceUriBuilder = new URIBuilder()
				.setScheme(GEOCODING_API_SCHEME)
				.setHost(GEOCODING_API_HOST)
				.setPath(GEOCODING_API_PATH);
		bind(URIBuilder.class).annotatedWith(Names.named(ANNOTATION_GEOCODING_API)).toInstance(geocodingApiServiceUriBuilder);
	}
}
