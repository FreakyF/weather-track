package org.weathertrack.api.service.geocoding.openmeteo.model;

import java.util.Arrays;
import java.util.Objects;

public record CityDataDTO(long id, String name,
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
                          long[] postcodes, long country_id,
                          String country,
                          String admin1,
                          String admin2,
                          String admin3,
                          String admin4) {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CityDataDTO that)) return false;
		return id() == that.id() && Double.compare(latitude(), that.latitude()) == 0
				&& Double.compare(longitude(), that.longitude()) == 0
				&& Double.compare(elevation(), that.elevation()) == 0
				&& admin1_id() == that.admin1_id() && admin2_id() == that.admin2_id()
				&& admin3_id() == that.admin3_id() && admin4_id() == that.admin4_id()
				&& country_id() == that.country_id() && Objects.equals(name(), that.name())
				&& Objects.equals(feature_code(), that.feature_code())
				&& Objects.equals(country_code(), that.country_code())
				&& Objects.equals(timezone(), that.timezone())
				&& Objects.equals(population(), that.population())
				&& Arrays.equals(postcodes(), that.postcodes())
				&& Objects.equals(country(), that.country())
				&& Objects.equals(admin1(), that.admin1())
				&& Objects.equals(admin2(), that.admin2())
				&& Objects.equals(admin3(), that.admin3())
				&& Objects.equals(admin4(), that.admin4());
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(id(),
				name(),
				latitude(),
				longitude(),
				elevation(),
				feature_code(),
				country_code(),
				admin1_id(),
				admin2_id(),
				admin3_id(),
				admin4_id(),
				timezone(),
				population(),
				country_id(),
				country(),
				admin1(),
				admin2(),
				admin3(),
				admin4());
		result = 31 * result + Arrays.hashCode(postcodes());
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
