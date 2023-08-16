package org.weathertrack.api.service.forecast.openmeteo;

import org.weathertrack.api.service.forecast.ForecastApiService;
import org.weathertrack.api.service.forecast.model.WeatherData;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.model.ResponseData;

public class OpenMeteoForecastApiService implements ForecastApiService {
	@Override
	public ResponseData<WeatherData> fetchForecastForCoordinates(GeocodingCityData geocodingCityData) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
