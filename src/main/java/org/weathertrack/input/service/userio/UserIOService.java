package org.weathertrack.input.service.userio;

import org.weathertrack.api.service.forecast.model.ForecastData;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;

import java.util.List;

public interface UserIOService {
	String getUserInput(String message);

	String getCityNameFromUser();

	void printCitiesWithSameName(List<GeocodingCityData> citiesWithSameName);

	void printWeather(ForecastData forecastData);
}
