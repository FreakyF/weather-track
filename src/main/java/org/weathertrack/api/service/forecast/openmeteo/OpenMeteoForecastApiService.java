package org.weathertrack.api.service.forecast.openmeteo;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.hc.core5.net.URIBuilder;
import org.weathertrack.api.service.forecast.ForecastApiModule;
import org.weathertrack.api.service.forecast.ForecastApiService;
import org.weathertrack.api.service.forecast.model.WeatherData;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.model.ResponseData;

public class OpenMeteoForecastApiService implements ForecastApiService {
	private final URIBuilder uriBuilder;
	private final HttpService httpService;

	@Inject
	public OpenMeteoForecastApiService(
			@Named(ForecastApiModule.ANNOTATION_FORECAST_API) URIBuilder uriBuilder,
			HttpService httpService) {
		this.uriBuilder = uriBuilder;
		this.httpService = httpService;
	}

	@Override
	public ResponseData<WeatherData> fetchForecastForCoordinates(GeocodingCityData geocodingCityData) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
