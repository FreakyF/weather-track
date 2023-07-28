package org.weathertrack.api.service.geocoding;

import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.geocoding.model.GeocodingData;

import java.util.List;

public interface GeocodingApiService {
    List<GeocodingCityData> fetchCitiesForCityName(String cityName);
    GeocodingData fetchGeocodingDataForCity(GeocodingCityData geocodingCityData);
}
