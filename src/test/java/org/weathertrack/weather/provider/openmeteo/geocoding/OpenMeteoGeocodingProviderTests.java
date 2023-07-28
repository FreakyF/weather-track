package org.weathertrack.weather.provider.openmeteo.geocoding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.weathertrack.weather.provider.openmeteo.model.city.CityData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenMeteoGeocodingProviderTests {
	GeocodingProvider geocodingProvider;

	@BeforeEach
	void setUp() {
		geocodingProvider = new OpenMeteoGeocodingProvider();
	}

	@Test
	void fetchCityDataFromCityName_WhenIsCorrect_ShouldReturnCityData() {
		// When
		var cityName = "Kielce";

		var expectedResponse = List.of(
				new CityData(769250,
						"Kielce",
						50.87033,
						20.62752,
						267,
						"PPLA",
						"PL",
						858790,
						7530962,
						7531642,
						0,
						"Europe/Warsaw",
						208598L,
						null,
						798544,
						"Poland",
						"Świętokrzyskie",
						"Kielce",
						"Kielce",
						null),
				new CityData(
						8533114,
						"Kielce-Masłów Airport",
						50.89819,
						20.72571,
						290,
						"AIRP",
						"PL",
						858790,
						7530797,
						7531818,
						0,
						"Europe/Warsaw",
						null,
						null,
						798544,
						"Poland",
						"Świętokrzyskie",
						"Powiat kielecki",
						"Gmina Masłów",
						null)
		);

		// Given
		var result = geocodingProvider.fetchCityDataFromCityName(cityName);

		// Then
		assertEquals(expectedResponse, result);
	}

	@Test
	void fetchCityDataFromCityName_WhenNameIsCorrect_ShouldReturnCityData() {
		// When
		var cityName = "Kielce";

		var expectedResponse = List.of(
				new CityData(769250,
						"Kielce",
						50.87033,
						20.62752,
						267,
						"PPLA",
						"PL",
						858790,
						7530962,
						7531642,
						0,
						"Europe/Warsaw",
						208598L,
						null,
						798544,
						"Poland",
						"Świętokrzyskie",
						"Kielce",
						"Kielce",
						null));

		// Given
		var result = geocodingProvider.fetchCityDataFromCityName(cityName);

		// Then
		for (int i = 0; i < expectedResponse.size(); i++) {
			assertEquals(expectedResponse.get(i).getName(), result.get(i).getName());
		}
	}

	// NoData
	@Test
	void fetchCityDataFromCityName_WhenNameIsCorrectButNoData_ShouldThrowException() {
		throw new UnsupportedOperationException();
	}

	// emptyCityName
	@Test
	void fetchCityDataFromCityName_WhenNameIsEmpty_ShouldThrowException() {
		throw new UnsupportedOperationException();

	}

	@Test
	void fetchCityDataFromCityName_WhenURISyntaxIsIncorrect_ShouldThrowException() {
		throw new UnsupportedOperationException();

	}
}
