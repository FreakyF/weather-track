package org.weathertrack.api.service;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.weathertrack.api.service.forecast.ForecastApiService;
import org.weathertrack.api.service.forecast.openmeteo.OpenMeteoForecastApiService;
import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.api.service.geocoding.openmeteo.OpenMeteoGeocodingApiService;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.http.json.JsonHttpService;

import java.net.http.HttpClient;

public class ApiServiceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(GeocodingApiService.class).to(OpenMeteoGeocodingApiService.class);
		bind(ForecastApiService.class).to(OpenMeteoForecastApiService.class);
		bind(HttpService.class).to(JsonHttpService.class);
	}

	@Provides
	protected HttpClient provideHttpClient() {
		return HttpClient.newHttpClient();
	}
}
