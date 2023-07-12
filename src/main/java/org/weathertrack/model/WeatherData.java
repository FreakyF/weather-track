package org.weathertrack.model;

public class WeatherData {
	private final String temperature;
	private final String cloudiness;
	private final String rainChance;
	private final String windSpeed;
	private final String weatherCondition;
	private final String humidity;
	private final String pressure;

	public WeatherData(String temperature,
	                   String cloudiness,
	                   String rainChance,
	                   String windSpeed,
	                   String weatherCondition,
	                   String humidity,
	                   String pressure) {
		this.temperature = temperature;
		this.cloudiness = cloudiness;
		this.rainChance = rainChance;
		this.windSpeed = windSpeed;
		this.weatherCondition = weatherCondition;
		this.humidity = humidity;
		this.pressure = pressure;
	}

	public String getTemperature() {
		return temperature;
	}

	public String getCloudiness() {
		return cloudiness;
	}

	public String getRainChance() {
		return rainChance;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public String getHumidity() {
		return humidity;
	}

	public String getPressure() {
		return pressure;
	}
}
