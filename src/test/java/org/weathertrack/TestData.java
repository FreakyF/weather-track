package org.weathertrack;

import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.geocoding.model.GeocodingData;

public class TestData {
	public static class Provider {
		public static GeocodingCityData createCityDataDTO() {
			return new GeocodingCityData(
					"Kielce",
					"Świętokrzyskie",
					"Poland"
			);
		}

		public static GeocodingData createGeocodingData() {
			return new GeocodingData(21, 37);
		}
	}

	private TestData() {
	}
}
