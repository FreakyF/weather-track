package org.weathertrack.api.service.geocoding;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.hc.core5.net.URIBuilder;

import static org.weathertrack.api.service.resource.ApiUriDataResource.getApiUriDataFromProperties;

public class GeocodingApiModule extends AbstractModule {
	public static final String ANNOTATION_GEOCODING_API = "ANNOTATION_GEOCODING_API";

	private static final String GEOCODING_API_SCHEME_PROPERTY_NAME = "GEOCODING_API_SCHEME";
	private static final String GEOCODING_API_HOST_PROPERTY_NAME = "GEOCODING_API_HOST";
	private static final String GEOCODING_API_PATH_PROPERTY_NAME = "GEOCODING_API_PATH";

	@Override
	protected void configure() {
		configureGeocodingApiUri();
	}

	protected void configureGeocodingApiUri() {
		var geocodingApiUriData = getApiUriDataFromProperties(GEOCODING_API_SCHEME_PROPERTY_NAME, GEOCODING_API_HOST_PROPERTY_NAME, GEOCODING_API_PATH_PROPERTY_NAME);
		var geocodingApiServiceUriBuilder = new URIBuilder()
				.setScheme(geocodingApiUriData.get(GEOCODING_API_SCHEME_PROPERTY_NAME))
				.setHost(geocodingApiUriData.get(GEOCODING_API_HOST_PROPERTY_NAME))
				.setPath(geocodingApiUriData.get(GEOCODING_API_PATH_PROPERTY_NAME));
		bind(URIBuilder.class).annotatedWith(Names.named(ANNOTATION_GEOCODING_API)).toInstance(geocodingApiServiceUriBuilder);
	}
}
