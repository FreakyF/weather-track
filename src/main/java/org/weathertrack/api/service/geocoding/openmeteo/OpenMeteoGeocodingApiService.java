package org.weathertrack.api.service.geocoding.openmeteo;

import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.geocoding.model.GeocodingData;

import java.util.List;

public class OpenMeteoGeocodingApiService implements GeocodingApiService {
    @Override
    public List<GeocodingCityData> fetchCitiesForCityName(String cityName) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public GeocodingData fetchGeocodingDataForCity(GeocodingCityData geocodingCityData) {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
