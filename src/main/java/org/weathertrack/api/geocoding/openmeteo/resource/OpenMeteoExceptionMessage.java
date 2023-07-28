package org.weathertrack.api.geocoding.openmeteo.resource;

public class OpenMeteoExceptionMessage {
	public static final String CITY_NAME_IS_NULL
			= "Tried to get city name but the city name is null!";
	public static final String CITY_NAME_IS_BLANK
			= "Tried to get city name but the city name is blank!";
	public static final String CITY_DATA_IS_NULL =
			"Tried to get city data but the city data is null";

	public static final String URI_IS_INVALID =
			"Tried to get URI but the URI is invalid";

	private OpenMeteoExceptionMessage() {
	}
}
