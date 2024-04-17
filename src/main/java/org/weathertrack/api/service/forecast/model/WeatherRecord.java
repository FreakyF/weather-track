package org.weathertrack.api.service.forecast.model;

public record WeatherRecord(double temperature,
                            int weatherCode,
                            double windSpeed,
                            int precipitation,
                            Integer humidity) {

	public WeatherRecord(double temperature,
	                     int weatherCode,
	                     double windSpeed,
	                     int precipitation) {
		this(temperature, weatherCode, windSpeed, precipitation, null);
	}
}
