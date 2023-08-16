package org.weathertrack;

import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.geocoding.openmeteo.model.GetCityDataRequest;

public class TestData {
	public static class Provider {
		public static GeocodingCityData createCityDataDTO() {
			return new GeocodingCityData(
					"Kielce",
					"Świętokrzyskie",
					"Poland"
			);
		}

		public static GetCityDataRequest createCityDataRequest() {
			return new GetCityDataRequest(21, 37);
		}
	}

	private TestData() {
	}
}
