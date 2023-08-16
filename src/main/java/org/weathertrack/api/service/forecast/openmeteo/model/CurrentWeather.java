package org.weathertrack.api.service.forecast.openmeteo.model;

import java.time.LocalDateTime;

public class CurrentWeather {
	private final LocalDateTime time;
	private final double temperature;
	private final int weathercode;
	private final double windspeed;
	private final int winddirection;

	public CurrentWeather(LocalDateTime time,
	                      double temperature,
	                      int weathercode,
	                      double windspeed,
	                      int winddirection) {
		this.time = time;
		this.temperature = temperature;
		this.weathercode = weathercode;
		this.windspeed = windspeed;
		this.winddirection = winddirection;
	}
}
