package org.weathertrack.geocoding;

import com.google.inject.AbstractModule;
import org.weathertrack.geocoding.api.ApiGeocodingProvider;

public class GeocodingModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(GeocodingProvider.class).to(ApiGeocodingProvider.class);
    }
}
