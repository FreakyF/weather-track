package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.Map;

public class ForecastReport {
	private double latitude;
	private double longitude;
	private double generationtime_ms;
	private int utc_offset_seconds;
	private String timezone;
	private String timezone_abbreviation;
	private double elevation;
	private Map<String, String> hourly_units;
	private Hourly hourly;
	private Map<String, String> daily_units;
	private Daily daily;

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
