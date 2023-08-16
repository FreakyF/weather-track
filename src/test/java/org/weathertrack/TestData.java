package org.weathertrack;

import org.weathertrack.api.service.geocoding.model.GeocodingCityData;

public class TestData {
	public static class Provider {
		public static GeocodingCityData createCityDataDTO() {
			return new GeocodingCityData(
					"Kielce",
					"Świętokrzyskie",
					"Poland"
			);
		}
	}

	private TestData() {
	}
}
