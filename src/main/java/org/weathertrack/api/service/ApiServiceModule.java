package org.weathertrack.api.service;

import com.google.inject.AbstractModule;
import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.api.service.geocoding.openmeteo.OpenMeteoGeocodingApiService;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.http.json.JsonHttpService;
import org.weathertrack.api.service.weather.WeatherApiService;
import org.weathertrack.api.service.weather.openmeteo.OpenMeteoWeatherApiService;

public class ApiServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GeocodingApiService.class).to(OpenMeteoGeocodingApiService.class);
        bind(WeatherApiService.class).to(OpenMeteoWeatherApiService.class);
        bind(HttpService.class).to(JsonHttpService.class);
    }
}
