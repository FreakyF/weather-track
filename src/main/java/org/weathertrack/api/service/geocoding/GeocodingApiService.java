package org.weathertrack.api.service.geocoding;

import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.GetCityDataRequest;
import org.weathertrack.model.ResponseData;

import java.util.List;

public interface GeocodingApiService {
	/**
	 * @param cityName name of the city to get detailed data for.
	 * @return List of detailed information for cities with given name.
	 * @throws IllegalArgumentException Throwed when cityName is null or is blank.
	 */
	ResponseData<List<CityDataDTO>> fetchCitiesForCityName(String cityName) throws IllegalArgumentException;

	ResponseData<CityDataDTO> fetchGeocodingDataForCity(GetCityDataRequest request);
}
