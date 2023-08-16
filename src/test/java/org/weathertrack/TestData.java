package org.weathertrack;

import org.weathertrack.api.service.forecast.openmeteo.model.CurrentWeather;
import org.weathertrack.api.service.forecast.openmeteo.model.HourlyUnits;
import org.weathertrack.api.service.forecast.openmeteo.model.HourlyWeather;
import org.weathertrack.api.service.forecast.openmeteo.model.WeatherReport;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;

import java.time.LocalDateTime;

public class TestData {
	public static class Provider {
		public static GeocodingCityData createCityDataDTO() {
			return new GeocodingCityData(
					"Kielce",
					"Świętokrzyskie",
					"Poland",
					21,
					37
			);
		}

		public static WeatherReport createWeatherReport() {
			LocalDateTime[] hourlyTime = {
					LocalDateTime.of(2023, 8, 16, 12, 0),
					LocalDateTime.of(2023, 8, 16, 13, 0),
					LocalDateTime.of(2023, 8, 16, 14, 0),

			};
			double[] hourlyTemperature = {
					23.5,
					24.2,
					25.0,
			};
			HourlyWeather hourlyWeather = new HourlyWeather(hourlyTime, hourlyTemperature);

			HourlyUnits hourlyUnits = new HourlyUnits("Celsius");

			LocalDateTime currentWeatherTime = LocalDateTime.now();
			CurrentWeather currentWeather = new CurrentWeather(currentWeatherTime, 25.0, 200, 5.0, 180);
			return new WeatherReport(
					21,
					37,
					283,
					1234.5,
					6,
					"Europe/Warsaw",
					"CET",
					hourlyWeather,
					hourlyUnits,
					currentWeather
			);
		}
	}

	private TestData() {
	}
}
