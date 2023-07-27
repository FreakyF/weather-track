package org.weathertrack.weather.provider.openmeteo.model;

public class CityData {
	String cityName;
	double latitude;
	double longitude;

	public CityData(String cityName, double latitude, double longitude) {
		this.cityName = cityName;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCityName() {
		return cityName;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
