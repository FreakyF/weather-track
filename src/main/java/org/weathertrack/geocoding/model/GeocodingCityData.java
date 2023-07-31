package org.weathertrack.geocoding.model;

public class GeocodingCityData {
	private final String name;
	private final String administration;
	private final String country;

	public GeocodingCityData(String name, String administration, String country) {
		this.name = name;
		this.administration = administration;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public String getAdministration() {
		return administration;
	}

	public String getCountry() {
		return country;
	}
}
