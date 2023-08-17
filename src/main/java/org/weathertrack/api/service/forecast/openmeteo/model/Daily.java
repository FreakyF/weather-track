package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.List;

public class Daily {
	private List<String> time;
	private List<Integer> weathercode;
	private List<Double> temperature_2m_max;
	private List<Integer> precipitation_probability_max;
	private List<Double> windspeed_10m_max;

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
