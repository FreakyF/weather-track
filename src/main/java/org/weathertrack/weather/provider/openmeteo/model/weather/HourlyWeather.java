package org.weathertrack.weather.provider.openmeteo.model.weather;

import java.time.LocalDateTime;

public class HourlyWeather {
	private LocalDateTime[] time;
	private double[] temperature_2m;
}
