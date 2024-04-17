package org.weathertrack.api.service.geocoding.openmeteo.model;

import java.util.List;

public class CityDataResponseDTO {
	private List<CityDataDTO> results;

	public List<CityDataDTO> getResults() {
		return results;
	}
}
