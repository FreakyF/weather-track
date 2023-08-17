package org.weathertrack.api.service.forecast.openmeteo.model;

public class CurrentWeather {
	private final String time;
	private final double temperature;
	private final int weathercode;
	private final double windspeed;
	private final int winddirection;

	public CurrentWeather(String time,
	                      double temperature,
	                      int weathercode,
	                      double windspeed,
	                      int winddirection) {
		this.time = time;
		this.temperature = temperature;
		this.weathercode = weathercode;
		this.windspeed = windspeed;
		this.winddirection = winddirection;
	}

	public String getTime() {
		return time;
	}

	public double getTemperature() {
		return temperature;
	}

	public int getWeathercode() {
		return weathercode;
	}

	public double getWindspeed() {
		return windspeed;
	}

	public int getWinddirection() {
		return winddirection;
	}
}
