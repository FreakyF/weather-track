package org.weathertrack.api.service.forecast;

import org.weathertrack.api.service.forecast.model.WeatherData;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.model.ResponseData;

public interface ForecastApiService {
	ResponseData<WeatherData> fetchForecastForCoordinates(GeocodingCityData geocodingCityData);
}
