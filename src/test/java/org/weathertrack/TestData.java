package org.weathertrack;

import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;

public class TestData {
	public static class Provider {
		public static CityDataDTO createCityDataDTO() {
			return new CityDataDTO(
					769250L,
					"Kielce",
					50.87033,
					20.62752,
					267.0,
					"PPLA",
					"PL",
					858790L,
					7530962L,
					7531642L,
					0,
					"Europe/Warsaw",
					208598L,
					null,
					798544,
					"Poland",
					"Świętokrzyskie",
					"Kielce",
					"Kielce",
					null
			);
		}
	}

	private TestData() {
	}
}
