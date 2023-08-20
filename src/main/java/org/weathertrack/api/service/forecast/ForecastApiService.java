package org.weathertrack.api.service.forecast;

import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.api.service.forecast.model.ForecastData;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.model.ResponseData;

import java.io.IOException;

public interface ForecastApiService {

	/**
	 * @param geocodingCityData contains the latitude and longitude of the location for which the forecast is requested.
	 * @return A {@link ResponseData} object containing the forecast data for the given coordinates.
	 * @throws BadRequestException  when statusCode is 400.
	 * @throws NotFoundException    when statusCode is 404.
	 * @throws IOException          when sendHttpGetRequest returns null.
	 * @throws InterruptedException when sendHttpGetRequest is interrupted.
	 */
	ResponseData<ForecastData> fetchForecastForCoordinates(GeocodingCityData geocodingCityData) throws BadRequestException, NotFoundException, IOException, InterruptedException;
}
