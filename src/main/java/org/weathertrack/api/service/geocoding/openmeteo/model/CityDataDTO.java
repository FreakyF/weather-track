package org.weathertrack.api.service.geocoding.openmeteo.model;

import java.util.Arrays;
import java.util.Objects;

public class CityDataDTO {
	private final long id;
	private final String name;
	private final double latitude;
	private final double longitude;
	private final double elevation;
	private final String feature_code;
	private final String country_code;
	private final long admin1_id;
	private final long admin2_id;
	private final long admin3_id;
	private final long admin4_id;
	private final String timezone;
	private final Long population;
	private final long[] postcodes;
	private final long country_id;
	private final String country;
	private final String admin1;
	private final String admin2;
	private final String admin3;
	private final String admin4;

	public CityDataDTO(long id,
	                   String name,
	                   double latitude,
	                   double longitude,
	                   double elevation,
	                   String feature_code,
	                   String country_code,
	                   long admin1_id,
	                   long admin2_id,
	                   long admin3_id,
	                   long admin4_id,
	                   String timezone,
	                   Long population,
	                   long[] postcodes,
	                   long country_id,
	                   String country,
	                   String admin1,
	                   String admin2,
	                   String admin3,
	                   String admin4) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
		this.feature_code = feature_code;
		this.country_code = country_code;
		this.admin1_id = admin1_id;
		this.admin2_id = admin2_id;
		this.admin3_id = admin3_id;
		this.admin4_id = admin4_id;
		this.timezone = timezone;
		this.population = population;
		this.postcodes = postcodes;
		this.country_id = country_id;
		this.country = country;
		this.admin1 = admin1;
		this.admin2 = admin2;
		this.admin3 = admin3;
		this.admin4 = admin4;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getElevation() {
		return elevation;
	}

	public String getFeature_code() {
		return feature_code;
	}

	public String getCountry_code() {
		return country_code;
	}

	public long getAdmin1_id() {
		return admin1_id;
	}

	public long getAdmin2_id() {
		return admin2_id;
	}

	public long getAdmin3_id() {
		return admin3_id;
	}

	public long getAdmin4_id() {
		return admin4_id;
	}

	public String getTimezone() {
		return timezone;
	}

	public Long getPopulation() {
		return population;
	}

	public long[] getPostcodes() {
		return postcodes;
	}

	public long getCountry_id() {
		return country_id;
	}

	public String getCountry() {
		return country;
	}

	public String getAdmin1() {
		return admin1;
	}

	public String getAdmin2() {
		return admin2;
	}

	public String getAdmin3() {
		return admin3;
	}

	public String getAdmin4() {
		return admin4;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CityDataDTO cityDataDTO)) return false;
		return getId() == cityDataDTO.getId() && Double.compare(cityDataDTO.getLatitude(), getLatitude()) == 0 && Double.compare(cityDataDTO.getLongitude(), getLongitude()) == 0 && Double.compare(cityDataDTO.getElevation(), getElevation()) == 0 && getAdmin1_id() == cityDataDTO.getAdmin1_id() && getAdmin2_id() == cityDataDTO.getAdmin2_id() && getAdmin3_id() == cityDataDTO.getAdmin3_id() && getAdmin4_id() == cityDataDTO.getAdmin4_id() && getCountry_id() == cityDataDTO.getCountry_id() && Objects.equals(getName(), cityDataDTO.getName()) && Objects.equals(getFeature_code(), cityDataDTO.getFeature_code()) && Objects.equals(getCountry_code(), cityDataDTO.getCountry_code()) && Objects.equals(getTimezone(), cityDataDTO.getTimezone()) && Objects.equals(getPopulation(), cityDataDTO.getPopulation()) && Arrays.equals(getPostcodes(), cityDataDTO.getPostcodes()) && Objects.equals(getCountry(), cityDataDTO.getCountry()) && Objects.equals(getAdmin1(), cityDataDTO.getAdmin1()) && Objects.equals(getAdmin2(), cityDataDTO.getAdmin2()) && Objects.equals(getAdmin3(), cityDataDTO.getAdmin3()) && Objects.equals(getAdmin4(), cityDataDTO.getAdmin4());
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(getId(), getName(), getLatitude(), getLongitude(), getElevation(), getFeature_code(), getCountry_code(), getAdmin1_id(), getAdmin2_id(), getAdmin3_id(), getAdmin4_id(), getTimezone(), getPopulation(), getCountry_id(), getCountry(), getAdmin1(), getAdmin2(), getAdmin3(), getAdmin4());
		result = 31 * result + Arrays.hashCode(getPostcodes());
		return result;
	}
}
