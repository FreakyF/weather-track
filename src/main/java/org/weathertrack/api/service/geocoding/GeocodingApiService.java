package org.weathertrack.api.service.geocoding;

import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.GetCityDataRequest;
import org.weathertrack.model.ResponseData;

import java.util.List;

public interface GeocodingApiService {
	ResponseData<List<CityDataDTO>> fetchCitiesForCityName(String cityName);

	ResponseData<CityDataDTO> fetchGeocodingDataForCity(GetCityDataRequest request);
}
