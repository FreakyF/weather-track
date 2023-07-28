package org.weathertrack.input.service.userio;

import org.weathertrack.api.geocoding.openmeteo.model.city.CityData;
import org.weathertrack.api.weather.model.WeatherData;

import java.util.List;

public interface UserIOService {
	String getUserInput(String message);

	String getCityNameFromUser();

	void printCitiesWithSameName(List<CityData> citiesWithSameName);

	void printWeather(WeatherData weatherData);
}
