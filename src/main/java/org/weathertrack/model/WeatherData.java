package org.weathertrack.model;

import org.weathertrack.util.logger.LoggerInterface;
import org.weathertrack.view.util.LogMessages;

public class WeatherData {

	private final String weatherCondition;
	private final double temperature;
	private final int cloudiness;
	private final int rainChance;
	private final double windSpeed;
	private final int humidity;
	private final int pressure;

	private final LoggerInterface<WeatherData> logger;

	public WeatherData(String weatherCondition,
	                   double temperature,
	                   int cloudiness,
	                   int rainChance,
	                   double windSpeed,
	                   int humidity,
	                   int pressure,
	                   LoggerInterface<WeatherData> logger) {
		this.weatherCondition = weatherCondition;
		this.temperature = temperature;
		this.cloudiness = cloudiness;
		this.rainChance = rainChance;
		this.windSpeed = windSpeed;
		this.humidity = humidity;
		this.pressure = pressure;
		this.logger = logger;
	}

	public String getWeatherCondition() {
		if (weatherCondition == null) {
			logger.error(LogMessages.WEATHER_CONDITION_IS_NULL);
			return null; // should i really return null?
		}

		var trimmedWeatherCondition = weatherCondition.trim();
		if (trimmedWeatherCondition.isEmpty()) {
			logger.error(LogMessages.WEATHER_CONDITION_IS_EMPTY);
		}
		return trimmedWeatherCondition;
	}

	public double getTemperature() {
		return temperature;
	}

	public int getCloudiness() {
		if (cloudiness > 100) {
			logger.error(LogMessages.CLOUDINESS_IS_ABOVE_100);
			// return something.
		} else if (cloudiness < 0) {
			logger.error(LogMessages.CLOUDINESS_IS_BELOW_0);
			// return something.
		}
		return cloudiness;
	}

	public int getRainChance() {
		if (rainChance > 100) {
			logger.error(LogMessages.RAIN_CHANCE_IS_ABOVE_100);
			// return something.
		} else if (rainChance < 0) {
			logger.error(LogMessages.RAIN_CHANCE_IS_BELOW_0);
			// return something.
		}
		return rainChance;
	}

	public double getWindSpeed() {
		if (windSpeed < 0) {
			logger.error(LogMessages.WIND_SPEED_IS_BELOW_0);
			// return something.
		}
		return windSpeed;
	}

	public int getHumidity() {
		if (humidity > 100) {
			logger.error(LogMessages.HUMIDITY_IS_ABOVE_100);
			// return something.
		} else if (humidity < 0) {
			logger.error(LogMessages.HUMIDITY_IS_BELOW_0);
			// return something.
		}
		return humidity;
	}

	public int getPressure() {
		if (pressure < 0) {
			logger.error(LogMessages.PRESSURE_IS_BELOW_0);
			// return something.
		}
		return pressure;
	}
}
