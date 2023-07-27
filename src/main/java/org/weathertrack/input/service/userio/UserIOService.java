package org.weathertrack.input.service.userio;

import org.weathertrack.weather.model.WeatherData;
import org.weathertrack.weather.provider.openmeteo.model.city.CityData;

import java.util.List;

public interface UserIOService {
	String getUserInput(String message);

	String getCityNameFromUser();

	void printCityDataWithSameCityName(List<CityData> citiesWithSameName);

	void printWeather(WeatherData weatherData);
}
