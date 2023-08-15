package org.weathertrack.api.service.geocoding.model;

public class GeocodingData {
	private final double Longitude;
	private final double Latitude;

	public GeocodingData(double longitude, double latitude) {
		Longitude = longitude;
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public double getLatitude() {
		return Latitude;
	}
}
