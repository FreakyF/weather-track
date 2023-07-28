package org.weathertrack.api.geocoding;

import com.google.inject.AbstractModule;
import org.weathertrack.api.geocoding.openmeteo.OpenMeteoGeocodingProvider;

public class GeocodingModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(GeocodingProvider.class).to(OpenMeteoGeocodingProvider.class);
	}
}
