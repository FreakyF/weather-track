package org.weathertrack.api.service.forecast.openmeteo.model;

import java.time.LocalDateTime;

public class HourlyWeather {
	private LocalDateTime[] time;
	private double[] temperature_2m;
}
