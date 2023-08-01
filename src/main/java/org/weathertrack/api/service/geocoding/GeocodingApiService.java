package org.weathertrack.api.service.geocoding;

import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataResponseDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.GetCityDataRequest;
import org.weathertrack.model.ResponseData;

import java.util.List;

public interface GeocodingApiService {
	ResponseData<List<CityDataResponseDTO>> fetchCitiesForCityName(String cityName);

	ResponseData<CityDataResponseDTO> fetchGeocodingDataForCity(GetCityDataRequest request);
}
