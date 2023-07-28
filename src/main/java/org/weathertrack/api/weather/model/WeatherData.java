package org.weathertrack.api.weather.model;

import org.weathertrack.api.weather.resource.WeatherExceptionMessage;

public record WeatherData(
		String weatherCondition,
		double temperatureCelsius,
		int cloudiness,
		int rainChance,
		double windSpeed,
		int humidity,
		int pressure) {
	private static final double ABSOLUTE_ZERO = -273.15;

	public WeatherData {
		validateWeatherCondition(weatherCondition);
		validateTemperatureCelsius(temperatureCelsius);
		validateCloudiness(cloudiness);
		validateRainChance(rainChance);
		validateWindSpeed(windSpeed);
		validateHumidity(humidity);
		validatePressure(pressure);
	}

	private void validateWeatherCondition(String weatherCondition) {
		if (weatherCondition == null) {
			throw new IllegalStateException(WeatherExceptionMessage.WEATHER_CONDITION_IS_NULL);
		}

		if (weatherCondition.isBlank()) {
			throw new IllegalStateException(WeatherExceptionMessage.WEATHER_CONDITION_IS_EMPTY);
		}
	}

	private void validateTemperatureCelsius(double temperatureCelsius) {
		if (temperatureCelsius < ABSOLUTE_ZERO) {
			throw new IllegalArgumentException(WeatherExceptionMessage.TEMPERATURE_IS_BELOW_ABSOLUTE_ZERO);
		}
	}

	private void validateCloudiness(int cloudiness) {
		if (cloudiness > 100) {
			throw new IllegalArgumentException(WeatherExceptionMessage.CLOUDINESS_IS_ABOVE_100);
		} else if (cloudiness < 0) {
			throw new IllegalArgumentException(WeatherExceptionMessage.CLOUDINESS_IS_BELOW_0);
		}
	}

	private void validateRainChance(int rainChance) {

		if (rainChance > 100) {
			throw new IllegalArgumentException(WeatherExceptionMessage.RAIN_CHANCE_IS_ABOVE_100);
		} else if (rainChance < 0) {
			throw new IllegalArgumentException(WeatherExceptionMessage.RAIN_CHANCE_IS_BELOW_0);
		}
	}

	private void validateWindSpeed(double windSpeed) {
		if (windSpeed < 0) {
			throw new IllegalArgumentException(WeatherExceptionMessage.WIND_SPEED_IS_BELOW_0);
		}
	}

	private void validateHumidity(int humidity) {
		if (humidity > 100) {
			throw new IllegalArgumentException(WeatherExceptionMessage.HUMIDITY_IS_ABOVE_100);
		} else if (humidity < 0) {
			throw new IllegalArgumentException(WeatherExceptionMessage.HUMIDITY_IS_BELOW_0);
		}
	}

	private void validatePressure(int pressure) {
		if (pressure < 0) {
			throw new IllegalArgumentException(WeatherExceptionMessage.PRESSURE_IS_BELOW_0);
		}
	}
}
