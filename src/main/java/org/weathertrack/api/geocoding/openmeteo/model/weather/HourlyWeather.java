package org.weathertrack.api.geocoding.openmeteo.model.weather;

import java.time.LocalDateTime;

public class HourlyWeather {
	private LocalDateTime[] time;
	private double[] temperature_2m;
}
