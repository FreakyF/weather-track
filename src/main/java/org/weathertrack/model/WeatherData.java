package org.weathertrack.model;

import org.weathertrack.view.util.LogMessages;

public class WeatherData {
	private static final double ABSOLUTE_ZERO = -273.15;
	private final String weatherCondition;
	private final double temperatureCelsius;
	private final int cloudiness;
	private final int rainChance;
	private final double windSpeed;
	private final int humidity;
	private final int pressure;

	public WeatherData(String weatherCondition,
	                   double temperatureCelsius,
	                   int cloudiness,
	                   int rainChance,
	                   double windSpeed,
	                   int humidity,
	                   int pressure) {

		validateWeatherCondition(weatherCondition);
		this.weatherCondition = weatherCondition;

		validateTemperature(temperatureCelsius);
		this.temperatureCelsius = temperatureCelsius;

		validateCloudiness(cloudiness);
		this.cloudiness = cloudiness;

		validateRainChance(rainChance);
		this.rainChance = rainChance;

		validateWindSpeed(windSpeed);
		this.windSpeed = windSpeed;

		validateHumidity(humidity);
		this.humidity = humidity;

		validatePressure(pressure);
		this.pressure = pressure;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public double getTemperatureCelsius() {
		return temperatureCelsius;
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

	private void validateWeatherCondition(String weatherCondition) {
		if (weatherCondition == null) {
			throw new IllegalStateException(LogMessages.WEATHER_CONDITION_IS_NULL);
		}

		if (weatherCondition.isBlank()) {
			throw new IllegalStateException(LogMessages.WEATHER_CONDITION_IS_EMPTY);
		}
	}

	// The temperature will be in Celsius and then calculated depending on the user choice of unit.
	private void validateTemperature(double temperature) {
		if (temperature < ABSOLUTE_ZERO) {
			throw new IllegalArgumentException(LogMessages.TEMPERATURE_IS_BELOW_ABSOLUTE_ZERO);
		}
	}

	private void validateCloudiness(int cloudiness) {
		if (cloudiness > 100) {
			throw new IllegalArgumentException(LogMessages.CLOUDINESS_IS_ABOVE_100);
		} else if (cloudiness < 0) {
			throw new IllegalArgumentException(LogMessages.CLOUDINESS_IS_BELOW_0);
		}
	}

	private void validateRainChance(int rainChance) {

		if (rainChance > 100) {
			throw new IllegalArgumentException(LogMessages.RAIN_CHANCE_IS_ABOVE_100);
		} else if (rainChance < 0) {
			throw new IllegalArgumentException(LogMessages.RAIN_CHANCE_IS_BELOW_0);
		}
	}

	private void validateWindSpeed(double windSpeed) {
		if (windSpeed < 0) {
			throw new IllegalArgumentException(LogMessages.WIND_SPEED_IS_BELOW_0);
		}
	}

	private void validateHumidity(int humidity) {
		if (humidity > 100) {
			throw new IllegalArgumentException(LogMessages.HUMIDITY_IS_ABOVE_100);
			// return something.
		} else if (humidity < 0) {
			throw new IllegalArgumentException(LogMessages.HUMIDITY_IS_BELOW_0);
		}
	}

	private void validatePressure(int pressure) {
		if (pressure < 0) {
			throw new IllegalArgumentException(LogMessages.PRESSURE_IS_BELOW_0);
		}
	}
}
