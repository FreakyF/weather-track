package org.weathertrack.api.service.forecast.openmeteo.model;

public class WeatherReport {
	private final double latitude;
	private final double longitude;
	private final double elevation;
	private final double generationtime_ms;
	private final int utc_offset_seconds;
	private final String timezone;
	private final String timezone_abbreviation;
	private final HourlyWeather hourly;
	private final HourlyUnits hourly_units;
	private final CurrentWeather current_weather;

	public WeatherReport(double latitude,
	                     double longitude,
	                     double elevation,
	                     double generationtime_ms,
	                     int utc_offset_seconds,
	                     String timezone,
	                     String timezone_abbreviation,
	                     HourlyWeather hourly,
	                     HourlyUnits hourly_units,
	                     CurrentWeather current_weather) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
		this.generationtime_ms = generationtime_ms;
		this.utc_offset_seconds = utc_offset_seconds;
		this.timezone = timezone;
		this.timezone_abbreviation = timezone_abbreviation;
		this.hourly = hourly;
		this.hourly_units = hourly_units;
		this.current_weather = current_weather;
	}
}
