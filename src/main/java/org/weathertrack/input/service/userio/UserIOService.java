package org.weathertrack.input.service.userio;

import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.forecast.model.WeatherData;

import java.util.List;

public interface UserIOService {
	String getUserInput(String message);

	String getCityNameFromUser();

	void printCitiesWithSameName(List<CityDataDTO> citiesWithSameName);

	void printWeather(WeatherData weatherData);
}
