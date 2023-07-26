package org.weathertrack.api.model;

import java.util.HashMap;
import java.util.Map;

public class WeatherInterpretation {
	private WeatherInterpretation() {
	}

	private static final Map<Integer, String> weatherDescriptions = new HashMap<>();

	static {
		weatherDescriptions.put(0, "Clear sky");
		weatherDescriptions.put(1, "Mainly clear");
		weatherDescriptions.put(2, "Partly cloudy");
		weatherDescriptions.put(3, "Overcast");
		weatherDescriptions.put(45, "Fog and depositing rime fog");
		weatherDescriptions.put(48, "Fog and depositing rime fog");
		weatherDescriptions.put(51, "Drizzle: Light intensity");
		weatherDescriptions.put(53, "Drizzle: Moderate intensity");
		weatherDescriptions.put(55, "Drizzle: Dense intensity");
		weatherDescriptions.put(56, "Freezing Drizzle: Light intensity");
		weatherDescriptions.put(57, "Freezing Drizzle: Dense intensity");
		weatherDescriptions.put(61, "Rain: Slight intensity");
		weatherDescriptions.put(63, "Rain: Moderate intensity");
		weatherDescriptions.put(65, "Rain: Heavy intensity");
		weatherDescriptions.put(66, "Freezing Rain: Light intensity");
		weatherDescriptions.put(67, "Freezing Rain: Heavy intensity");
		weatherDescriptions.put(71, "Snow fall: Slight intensity");
		weatherDescriptions.put(73, "Snow fall: Moderate intensity");
		weatherDescriptions.put(75, "Snow fall: Heavy intensity");
		weatherDescriptions.put(77, "Snow grains");
		weatherDescriptions.put(80, "Rain showers: Slight intensity");
		weatherDescriptions.put(81, "Rain showers: Moderate intensity");
		weatherDescriptions.put(82, "Rain showers: Violent intensity");
		weatherDescriptions.put(85, "Snow showers: Slight intensity");
		weatherDescriptions.put(86, "Snow showers: Heavy intensity");
		weatherDescriptions.put(95, "Thunderstorm: Slight or moderate");
		weatherDescriptions.put(96, "Thunderstorm with slight hail");
		weatherDescriptions.put(99, "Thunderstorm with heavy hail");
	}

	public static String interpretWeatherCode(int code) {
		return weatherDescriptions.getOrDefault(code, "Unknown weather code");
	}
}
