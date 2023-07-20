package org.weathertrack.util.validation;

import org.weathertrack.view.util.LogMessages;

public class WeatherDataValidator {
	private static final double ABSOLUTE_ZERO = -273.15;

	public String validateWeatherCondition(String weatherCondition) {
		if (weatherCondition == null) {
			throw new IllegalStateException(LogMessages.WEATHER_CONDITION_IS_NULL);
		}
		var trimmedWeatherCondition = weatherCondition.trim();
		if (trimmedWeatherCondition.isEmpty()) {
			throw new IllegalStateException(LogMessages.WEATHER_CONDITION_IS_EMPTY);
		}
		return trimmedWeatherCondition;
	}

	// The temperature will be in Celsius and then calculated depending on the user choice of unit.
	public double validateTemperature(double temperature) {
		if (temperature < ABSOLUTE_ZERO) {
			throw new IllegalArgumentException(LogMessages.TEMPERATURE_IS_BELOW_ABSOLUTE_ZERO);
		}
		return temperature;
	}

	public int validateCloudiness(int cloudiness) {
		if (cloudiness > 100) {
			throw new IllegalArgumentException(LogMessages.CLOUDINESS_IS_ABOVE_100);
		} else if (cloudiness < 0) {
			throw new IllegalArgumentException(LogMessages.CLOUDINESS_IS_BELOW_0);
		}
		return cloudiness;
	}

	public int validateRainChance(int rainChance) {

		if (rainChance > 100) {
			throw new IllegalArgumentException(LogMessages.RAIN_CHANCE_IS_ABOVE_100);
		} else if (rainChance < 0) {
			throw new IllegalArgumentException(LogMessages.RAIN_CHANCE_IS_BELOW_0);
		}
		return rainChance;
	}

	public double validateWindSpeed(double windSpeed) {
		if (windSpeed < 0) {
			throw new IllegalArgumentException(LogMessages.WIND_SPEED_IS_BELOW_0);
		}
		return windSpeed;
	}

	public int validateHumidity(int humidity) {
		if (humidity > 100) {
			throw new IllegalArgumentException(LogMessages.HUMIDITY_IS_ABOVE_100);
			// return something.
		} else if (humidity < 0) {
			throw new IllegalArgumentException(LogMessages.HUMIDITY_IS_BELOW_0);
		}
		return humidity;
	}

	public int validatePressure(int pressure) {
		if (pressure < 0) {
			throw new IllegalArgumentException(LogMessages.PRESSURE_IS_BELOW_0);
		}
		return pressure;
	}
}
