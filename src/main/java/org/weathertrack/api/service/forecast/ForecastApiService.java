package org.weathertrack.api.service.forecast;

import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.api.service.forecast.model.ForecastData;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.model.ResponseData;

import java.io.IOException;

public interface ForecastApiService {
	ResponseData<ForecastData> fetchForecastForCoordinates(GeocodingCityData geocodingCityData) throws BadRequestException, NotFoundException, IOException, InterruptedException;
}
