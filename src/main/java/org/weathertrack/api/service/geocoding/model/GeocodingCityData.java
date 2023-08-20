package org.weathertrack.api.service.geocoding.model;

import java.util.Objects;

public record GeocodingCityData(String name, String administration, String country, double latitude, double longitude) {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GeocodingCityData that)) return false;
		return Double.compare(latitude, that.latitude) == 0
				&& Double.compare(longitude, that.longitude) == 0
				&& Objects.equals(name, that.name)
				&& Objects.equals(administration, that.administration)
				&& Objects.equals(country, that.country);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, administration, country, latitude, longitude);
	}
}
