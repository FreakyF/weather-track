package org.weathertrack.api.service.forecast.model;

public enum Unit {
	CELSIUS, FAHRENHEIT, KELVIN, // Temperature units
	MM, INCH, // Precipitation units
	MPH, KPH, // Wind speed units
	HPA, INHG, // Pressure units
	PERCENT, // Humidity unit
	NONE; // No unit

	public static Unit fromJsonKey(String jsonKey) {
		return switch (jsonKey.toLowerCase()) {
			case "temperature_2m", "temperature_2m_max" -> CELSIUS;
			case "precipitation", "precipitation_probability_max" -> MM;
			case "windspeed_10m", "windspeed_10m_max" -> KPH;
			case "surface_pressure" -> HPA;
			case "relativehumidity_2m" -> PERCENT;
			case "time", "weathercode" -> NONE;
			default -> throw new IllegalArgumentException("Unknown unit: " + jsonKey);
		};
	}
}
