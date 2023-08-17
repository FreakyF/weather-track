package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.List;

public class WeatherReportResponseDTO {
	private List<WeatherReport> results;

	public List<WeatherReport> getResults() {
		return results;
	}
}
