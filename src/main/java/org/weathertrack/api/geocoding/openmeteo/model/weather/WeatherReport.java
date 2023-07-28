package org.weathertrack.api.geocoding.openmeteo.model.weather;

public class WeatherReport {
	private double latitude;
	private double longitude;
	private double elevation;
	private double generationtime_ms;
	private int utc_offset_seconds;
	private String timezone;
	private String timezone_abbreviation;
	private HourlyWeather hourly;
	private HourlyUnits hourly_units;
	private CurrentWeather current_weather;
}
