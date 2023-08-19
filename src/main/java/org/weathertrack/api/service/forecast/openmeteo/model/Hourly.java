package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.List;

public class Hourly {
	private final List<String> time;
	private final List<Double> temperature_2m;
	private final List<Integer> relativehumidity_2m;
	private final List<Double> precipitation;
	private final List<Integer> weathercode;
	private final List<Double> surface_pressure;
	private final List<Double> windspeed_10m;

	public Hourly(List<String> time,
	              List<Double> temperature_2m,
	              List<Integer> relativehumidity_2m,
	              List<Double> precipitation,
	              List<Integer> weathercode,
	              List<Double> surface_pressure,
	              List<Double> windspeed_10m) {
		this.time = time;
		this.temperature_2m = temperature_2m;
		this.relativehumidity_2m = relativehumidity_2m;
		this.precipitation = precipitation;
		this.weathercode = weathercode;
		this.surface_pressure = surface_pressure;
		this.windspeed_10m = windspeed_10m;
	}

	public List<String> getTime() {
		return time;
	}

	public List<Double> getTemperature_2m() {
		return temperature_2m;
	}

	public List<Integer> getRelativehumidity_2m() {
		return relativehumidity_2m;
	}

	public List<Double> getPrecipitation() {
		return precipitation;
	}

	public List<Integer> getWeathercode() {
		return weathercode;
	}

	public List<Double> getSurface_pressure() {
		return surface_pressure;
	}

	public List<Double> getWindspeed_10m() {
		return windspeed_10m;
	}
}
