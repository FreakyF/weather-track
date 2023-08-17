package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.List;

public class Hourly {
	private List<String> time;
	private List<Double> temperature_2m;
	private List<Integer> relativehumidity_2m;
	private List<Double> precipitation;
	private List<Integer> weathercode;
	private List<Double> surface_pressure;
	private List<Double> windspeed_10m;

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
