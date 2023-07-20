package org.weathertrack.model;

import org.weathertrack.util.validation.WeatherDataValidator;

public class WeatherData {

	private final String weatherCondition;
	private final double temperature;
	private final int cloudiness;
	private final int rainChance;
	private final double windSpeed;
	private final int humidity;
	private final int pressure;

	public WeatherData(String weatherCondition,
	                   double temperature,
	                   int cloudiness,
	                   int rainChance,
	                   double windSpeed,
	                   int humidity,
	                   int pressure) {
		WeatherDataValidator weatherDataValidator = new WeatherDataValidator();

		this.weatherCondition = weatherDataValidator.validateWeatherCondition(weatherCondition);
		this.temperature = weatherDataValidator.validateTemperature(temperature);
		this.cloudiness = weatherDataValidator.validateCloudiness(cloudiness);
		this.rainChance = weatherDataValidator.validateRainChance(rainChance);
		this.windSpeed = weatherDataValidator.validateWindSpeed(windSpeed);
		this.humidity = weatherDataValidator.validateHumidity(humidity);
		this.pressure = weatherDataValidator.validatePressure(pressure);
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public double getTemperature() {
		return temperature;
	}

	public int getCloudiness() {
		return cloudiness;
	}

	public int getRainChance() {
		return rainChance;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public int getHumidity() {
		return humidity;
	}

	public int getPressure() {
		return pressure;
	}
}
