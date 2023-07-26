package org.weathertrack;

import org.weathertrack.api.APIService;
import org.weathertrack.api.OpenMeteoAPIService;

public class Main {
	public static void main(String[] args) {
		APIService apiService = new OpenMeteoAPIService();
		var x = apiService.fetchCoordinatesFromCityName("Kielce");
		System.out.println(x.latitude() + " " + x.longitude());
	}
}
