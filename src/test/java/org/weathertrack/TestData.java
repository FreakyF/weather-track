package org.weathertrack;

import org.weathertrack.api.service.forecast.model.ForecastData;
import org.weathertrack.api.service.forecast.model.Unit;
import org.weathertrack.api.service.forecast.model.WeatherRecord;
import org.weathertrack.api.service.forecast.openmeteo.model.Daily;
import org.weathertrack.api.service.forecast.openmeteo.model.ForecastReport;
import org.weathertrack.api.service.forecast.openmeteo.model.Hourly;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

			WeatherRecord dailyWeatherRecord = new WeatherRecord(25.5, 0, 8.3, 5);
			WeatherRecord hourlyWeatherRecord = new WeatherRecord(22.0, 1, 10.1, 2, 2);
			String dateTimeString = "2023-08-18T21:04:16.056821";

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
			LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
			builder.addDailyRecord(localDateTime, dailyWeatherRecord);
			builder.addHourlyRecord(localDateTime, hourlyWeatherRecord);

			builder.addUnit(Unit.CELSIUS, "°C");
			builder.addUnit(Unit.KPH, "Kph");
			builder.addUnit(Unit.PERCENT, "%");
			builder.addUnit(Unit.MM, "mm");

			ForecastData.Builder.setUtcOffsetSeconds(3600);

			ForecastData.Builder.setTimeZone(ZoneId.of("Europe/Warsaw"));

			return builder.build();
		}

		public static ForecastReport createForecastReport() {
			Map<String, String> hourlyUnits = new HashMap<>();
			hourlyUnits.put("temperature_2m", "°C");
			hourlyUnits.put("relativehumidity_2m", "%");

			Hourly hourly = createHourly();

			Map<String, String> dailyUnits = new HashMap<>();
			dailyUnits.put("temperature_2m_max", "°C");
			dailyUnits.put("precipitation_probability_max", "%");

			Daily daily = createDaily();

			return new ForecastReport(21,
					37,
					3600,
					3600,
					"Europe/Warsaw",
					"UTC",
					50,
					hourlyUnits,
					hourly,
					dailyUnits,
					daily
			);
		}

		private static Hourly createHourly() {
			List<String> time = new ArrayList<>();
			time.add("2023-08-19T12:00:00");
			time.add("2023-08-19T13:00:00");

			List<Double> temperature_2m = new ArrayList<>();
			temperature_2m.add(25.0);
			temperature_2m.add(26.5);

			List<Integer> relativehumidity_2m = new ArrayList<>();
			relativehumidity_2m.add(60);
			relativehumidity_2m.add(55);

			List<Double> precipitation = new ArrayList<>();
			precipitation.add(0.0);
			precipitation.add(0.1);

			List<Integer> weathercode = new ArrayList<>();
			weathercode.add(0);
			weathercode.add(1);

			List<Double> surface_pressure = new ArrayList<>();
			surface_pressure.add(1013.25);
			surface_pressure.add(1012.75);

			List<Double> windspeed_10m = new ArrayList<>();
			windspeed_10m.add(5.0);
			windspeed_10m.add(6.0);

			return new Hourly(time,
					temperature_2m,
					relativehumidity_2m,
					precipitation,
					weathercode,
					surface_pressure,
					windspeed_10m);
		}

		private static Daily createDaily() {
			List<String> time = new ArrayList<>();
			time.add("2023-08-19");
			time.add("2023-08-20");

			List<Integer> weathercode = new ArrayList<>();
			weathercode.add(0);
			weathercode.add(1);

			List<Double> temperature_2m_max = new ArrayList<>();
			temperature_2m_max.add(28.0);
			temperature_2m_max.add(29.5);

			List<Integer> precipitation_probability_max = new ArrayList<>();
			precipitation_probability_max.add(20);
			precipitation_probability_max.add(30);

			List<Double> windspeed_10m_max = new ArrayList<>();
			windspeed_10m_max.add(8.0);
			windspeed_10m_max.add(9.0);

			return new Daily(time,
					weathercode,
					temperature_2m_max,
					precipitation_probability_max,
					windspeed_10m_max);
		}
	}

	private TestData() {
	}
}
