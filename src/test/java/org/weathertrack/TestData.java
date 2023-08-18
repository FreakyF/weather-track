package org.weathertrack;

import org.weathertrack.api.service.forecast.model.ForecastData;
import org.weathertrack.api.service.forecast.model.Unit;
import org.weathertrack.api.service.forecast.model.WeatherRecord;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TestData {
	public static class Provider {
		public static CityDataDTO createCityDataDTO() {
			return new CityDataDTO(
					1,
					"Kielce",
					21.0,
					37.0,
					202.2,
					"PL",
					"PL",
					12312312L,
					12312312L,
					12312312L,
					0L,
					"Europe/Warsaw",
					12313123123L,
					null,
					6969L,
					"Poland",
					"Świętokrzyskie",
					"Świętokrzyskie",
					"Świętokrzyskie",
					null
			);
		}

		public static GeocodingCityData createGeocodingCityData() {
			return new GeocodingCityData(
					"Kielce",
					"Świętokrzyskie",
					"Poland",
					21,
					37
			);
		}

		public static ForecastData createForecastData() {
			ForecastData.Builder builder = new ForecastData.Builder();

			WeatherRecord dailyWeatherRecord = new WeatherRecord(25.5, 200, 8.3, 5);
			WeatherRecord hourlyWeatherRecord = new WeatherRecord(22.0, 300, 10.1, 2, 2);
			builder.addDailyRecord(LocalDateTime.now(), dailyWeatherRecord);
			builder.addHourlyRecord(LocalDateTime.now(), hourlyWeatherRecord);

			builder.addUnit(Unit.CELSIUS, "°C");

			builder.setUtcOffsetSeconds(3600);

			builder.setTimeZone(ZoneId.of("UTC"));

			return builder.build();
		}
	}

	private TestData() {
	}
}
