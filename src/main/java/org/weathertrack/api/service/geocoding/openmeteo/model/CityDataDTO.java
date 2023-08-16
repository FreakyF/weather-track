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
		if (!(o instanceof CityDataDTO that)) return false;
		return getId() == that.getId() && Double.compare(getLatitude(), that.getLatitude()) == 0 && Double.compare(getLongitude(), that.getLongitude()) == 0 && Double.compare(getElevation(), that.getElevation()) == 0 && getAdmin1_id() == that.getAdmin1_id() && getAdmin2_id() == that.getAdmin2_id() && getAdmin3_id() == that.getAdmin3_id() && getAdmin4_id() == that.getAdmin4_id() && getCountry_id() == that.getCountry_id() && Objects.equals(getName(), that.getName()) && Objects.equals(getFeature_code(), that.getFeature_code()) && Objects.equals(getCountry_code(), that.getCountry_code()) && Objects.equals(getTimezone(), that.getTimezone()) && Objects.equals(getPopulation(), that.getPopulation()) && Arrays.equals(getPostcodes(), that.getPostcodes()) && Objects.equals(getCountry(), that.getCountry()) && Objects.equals(getAdmin1(), that.getAdmin1()) && Objects.equals(getAdmin2(), that.getAdmin2()) && Objects.equals(getAdmin3(), that.getAdmin3()) && Objects.equals(getAdmin4(), that.getAdmin4());
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(getId(), getName(), getLatitude(), getLongitude(), getElevation(), getFeature_code(), getCountry_code(), getAdmin1_id(), getAdmin2_id(), getAdmin3_id(), getAdmin4_id(), getTimezone(), getPopulation(), getCountry_id(), getCountry(), getAdmin1(), getAdmin2(), getAdmin3(), getAdmin4());
		result = 31 * result + Arrays.hashCode(getPostcodes());
		return result;
	}

	@Override
	public String toString() {
		return "CityDataDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", elevation=" + elevation +
				", feature_code='" + feature_code + '\'' +
				", country_code='" + country_code + '\'' +
				", admin1_id=" + admin1_id +
				", admin2_id=" + admin2_id +
				", admin3_id=" + admin3_id +
				", admin4_id=" + admin4_id +
				", timezone='" + timezone + '\'' +
				", population=" + population +
				", postcodes=" + Arrays.toString(postcodes) +
				", country_id=" + country_id +
				", country='" + country + '\'' +
				", admin1='" + admin1 + '\'' +
				", admin2='" + admin2 + '\'' +
				", admin3='" + admin3 + '\'' +
				", admin4='" + admin4 + '\'' +
				'}';
	}
}
