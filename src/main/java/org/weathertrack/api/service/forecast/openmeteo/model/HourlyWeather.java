package org.weathertrack.api.service.forecast.openmeteo.model;

import java.time.LocalDateTime;

public class HourlyWeather {
	private final LocalDateTime[] time;
	private final double[] temperature_2m;

	public HourlyWeather(LocalDateTime[] time, double[] temperature_2m) {
		this.time = time;
		this.temperature_2m = temperature_2m;
	}
}
