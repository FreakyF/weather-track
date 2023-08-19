package org.weathertrack.api.service.geocoding;

import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.model.ResponseData;

import java.io.IOException;
import java.util.List;

public interface GeocodingApiService {
	// TODO: Improve documentation. Logic is not correct. sendHttpGetRequest CANNOT BE null.

	/**
	 * @param cityName name of the city to get detailed data for.
	 * @return List of detailed information for cities with given name.
	 * @throws IllegalArgumentException when cityName is null or is blank.
	 * @throws IOException              when sendHttpGetRequest is null.
	 * @throws InterruptedException     when sendHttpGetRequest is interrupted.
	 */
	ResponseData<List<GeocodingCityData>> fetchCitiesForCityName(String cityName) throws IllegalArgumentException, IOException, InterruptedException, BadRequestException, NotFoundException;
}
