package org.weathertrack.api.service.geocoding.model;

public class GeocodingData {
	private final double longitude;
	private final double latitude;

	public GeocodingData(double longitude, double latitude) {
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
