package org.weathertrack.api.provider.openmeteo.geocoding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.weathertrack.api.geocoding.GeocodingProvider;
import org.weathertrack.api.geocoding.openmeteo.OpenMeteoGeocodingProvider;
import org.weathertrack.api.geocoding.openmeteo.model.city.CityData;
import org.weathertrack.api.geocoding.openmeteo.resource.OpenMeteoExceptionMessage;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class OpenMeteoGeocodingProviderTests {
	GeocodingProvider geocodingProvider;

	@BeforeEach
	void setUp() {
		geocodingProvider = new OpenMeteoGeocodingProvider();
	}

	@Test
	void fetchCityData_WhenIsValid_ShouldReturnCityData() {
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
		var result = geocodingProvider.fetchCityData(cityName);

		// Then
		assertEquals(expectedResponse, result);
	}

	@Test
	void fetchCityData_WhenNameIsValid_ShouldReturnCityData() {
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
		var result = geocodingProvider.fetchCityData(cityName);

		// Then
		for (int i = 0; i < expectedResponse.size(); i++) {
			assertEquals(expectedResponse.get(i).getName(), result.get(i).getName());
		}
	}

	@Test
	void fetchCityData_WhenNameIsValidButNoData_ShouldThrowNullPointerException_WithAppropriateMessage() {
		// Given
		var cityName = "Kielce";
		var geocodingProvider = mock(OpenMeteoGeocodingProvider.class);

		when(geocodingProvider.fetchCityData(cityName)).thenThrow(new NullPointerException(OpenMeteoExceptionMessage.CITY_DATA_IS_NULL));

		// When
		var throwedException = assertThrows(NullPointerException.class, () -> {
			geocodingProvider.fetchCityData(cityName);
		});

		// Then
		assertEquals(OpenMeteoExceptionMessage.CITY_DATA_IS_NULL, throwedException.getMessage());
	}

	private static Stream<Arguments> fetchCityData_WhenCityNameIsInvalid_ShouldThrowIllegalStateException_WithAppropriateMessage() {
		return Stream.of(
				Arguments.of("", OpenMeteoExceptionMessage.CITY_NAME_IS_BLANK),
				Arguments.of(" ", OpenMeteoExceptionMessage.CITY_NAME_IS_BLANK),
				Arguments.of(null, OpenMeteoExceptionMessage.CITY_NAME_IS_NULL)
		);
	}

	@ParameterizedTest
	@MethodSource
	void fetchCityData_WhenCityNameIsInvalid_ShouldThrowIllegalStateException_WithAppropriateMessage(String cityNameValue, String expectedException) {
		// Given
		var geocodingProvider = new OpenMeteoGeocodingProvider();

		// When
		var throwedException = assertThrows(IllegalStateException.class, () -> {
			geocodingProvider.fetchCityData(cityNameValue);
		});

		// Then
		assertEquals(expectedException, throwedException.getMessage());
	}

	@Test
	void fetchCityData_WhenURISyntaxIsIncorrect_ShouldThrowURISyntaxException_WithAppropriateMessage() {
		// Given
		var cityName = "Kielce";
		var geocodingProvider = new OpenMeteoGeocodingProvider();

		// When
		var mockedGeocodingProvider = spy(geocodingProvider);
		doThrow(URISyntaxException.class).when(mockedGeocodingProvider).fetchCityData(any());

		var throwedException = assertThrows(URISyntaxException.class, () -> {
			geocodingProvider.fetchCityData(cityName);
		});

		// Then
		assertEquals(OpenMeteoExceptionMessage.URI_IS_INVALID, throwedException.getMessage());
	}
}
