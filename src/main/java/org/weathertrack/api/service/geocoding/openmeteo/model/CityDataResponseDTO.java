package org.weathertrack.api.service.geocoding.openmeteo.model;

import org.weathertrack.api.service.geocoding.model.GeocodingCityData;

import java.util.List;

public class CityDataResponseDTO {
	private List<GeocodingCityData> results;

	public List<GeocodingCityData> getResults() {
		return results;
	}
}
