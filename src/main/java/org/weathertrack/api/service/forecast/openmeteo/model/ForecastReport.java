package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.Map;

public class ForecastReport {
	private final double latitude;
	private final double longitude;
	private final double generationtime_ms;
	private final int utc_offset_seconds;
	private final String timezone;
	private final String timezone_abbreviation;
	private final double elevation;
	private final Map<String, String> hourly_units;
	private final Hourly hourly;
	private final Map<String, String> daily_units;
	private final Daily daily;

	public ForecastReport(double latitude, double longitude, double generationtime_ms, int utc_offset_seconds, String timezone, String timezone_abbreviation, double elevation, Map<String, String> hourly_units, Hourly hourly, Map<String, String> daily_units, Daily daily) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.generationtime_ms = generationtime_ms;
		this.utc_offset_seconds = utc_offset_seconds;
		this.timezone = timezone;
		this.timezone_abbreviation = timezone_abbreviation;
		this.elevation = elevation;
		this.hourly_units = hourly_units;
		this.hourly = hourly;
		this.daily_units = daily_units;
		this.daily = daily;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getGenerationtime_ms() {
		return generationtime_ms;
	}

	public int getUtc_offset_seconds() {
		return utc_offset_seconds;
	}

	public String getTimezone() {
		return timezone;
	}

	public String getTimezone_abbreviation() {
		return timezone_abbreviation;
	}

	public double getElevation() {
		return elevation;
	}

	public Map<String, String> getHourly_units() {
		return hourly_units;
	}

	public Hourly getHourly() {
		return hourly;
	}

	public Map<String, String> getDaily_units() {
		return daily_units;
	}

	public Daily getDaily() {
		return daily;
	}
}
