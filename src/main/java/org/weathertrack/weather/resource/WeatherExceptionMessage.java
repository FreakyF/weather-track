package org.weathertrack.weather.resource;

public class WeatherExceptionMessage {
	public static final String WEATHER_CONDITION_IS_NULL
			= "Tried to get weather condition but the weather condition is null!";
	public static final String WEATHER_CONDITION_IS_EMPTY
			= "Tried to get weather condition but the weather condition is empty!";
	public static final String TEMPERATURE_IS_BELOW_ABSOLUTE_ZERO
			= "Tried to get temperature but the temperature is below the absolute zero!";
	public static final String CLOUDINESS_IS_ABOVE_100
			= "Tried to get cloudiness but cloudiness is above 100%";
	public static final String CLOUDINESS_IS_BELOW_0
			= "Tried to get cloudiness but cloudiness is below 0%";
	public static final String RAIN_CHANCE_IS_ABOVE_100
			= "Tried to get rain chance but rain chance is above 100%";
	public static final String RAIN_CHANCE_IS_BELOW_0
			= "Tried to get rain chance but rain chance is below 0%";
	public static final String WIND_SPEED_IS_BELOW_0
			= "Tried to get wind speed but wind speed is below 0.";
	public static final String HUMIDITY_IS_ABOVE_100
			= "Tried to get humidity but humidity is above 100%";
	public static final String HUMIDITY_IS_BELOW_0
			= "Tried to get humidity but humidity is below 0%";
	public static final String PRESSURE_IS_BELOW_0
			= "Tried to get pressure but pressure is below 0.";

	private WeatherExceptionMessage() {
	}
}
