package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.List;

public class Daily {
	private final List<String> time;
	private final List<Integer> weathercode;
	private final List<Double> temperature_2m_max;
	private final List<Integer> precipitation_probability_max;
	private final List<Double> windspeed_10m_max;

	public Daily(List<String> time,
	             List<Integer> weathercode,
	             List<Double> temperature_2m_max,
	             List<Integer> precipitation_probability_max,
	             List<Double> windspeed_10m_max) {
		this.time = time;
		this.weathercode = weathercode;
		this.temperature_2m_max = temperature_2m_max;
		this.precipitation_probability_max = precipitation_probability_max;
		this.windspeed_10m_max = windspeed_10m_max;
	}

	public List<String> getTime() {
		return time;
	}

	public List<Integer> getWeathercode() {
		return weathercode;
	}

	public List<Double> getTemperature_2m_max() {
		return temperature_2m_max;
	}

	public List<Integer> getPrecipitation_probability_max() {
		return precipitation_probability_max;
	}

	public List<Double> getWindspeed_10m_max() {
		return windspeed_10m_max;
	}
}
