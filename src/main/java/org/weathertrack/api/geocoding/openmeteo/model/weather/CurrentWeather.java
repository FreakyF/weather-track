package org.weathertrack.api.geocoding.openmeteo.model.weather;

import java.time.LocalDateTime;

public class CurrentWeather {
	private LocalDateTime time;
	private double temperature;
	private int weathercode;
	private double windspeed;
	private int winddirection;
}
