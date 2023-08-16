package org.weathertrack.api.service.geocoding.openmeteo.model;

public class GetCityDataRequest {
	private final double longitude;
	private final double latitude;

	public GetCityDataRequest(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}
}
