package org.weathertrack.model;

public class WeatherData {
	private final String weatherCondition;
	private final float temperature;
	private final int cloudiness;
	private final int rainChance;
	private final float windSpeed;
	private final int humidity;
	private final int pressure;

	public WeatherData(String weatherCondition,
	                   float temperature,
	                   int cloudiness,
	                   int rainChance,
	                   float windSpeed,
	                   int humidity,
	                   int pressure) {
		this.weatherCondition = weatherCondition;
		this.temperature = temperature;
		this.cloudiness = cloudiness;
		this.rainChance = rainChance;
		this.windSpeed = windSpeed;
		this.humidity = humidity;
		this.pressure = pressure;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public float getTemperature() {
		return temperature;
	}

	public int getCloudiness() {
		return cloudiness;
	}

	public int getRainChance() {
		return rainChance;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public int getHumidity() {
		return humidity;
	}

	public int getPressure() {
		return pressure;
	}
}
