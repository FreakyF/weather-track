package org.weathertrack.api.service.forecast.openmeteo.converter;

import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.forecast.model.ForecastData;
import org.weathertrack.api.service.forecast.model.Unit;
import org.weathertrack.api.service.forecast.model.WeatherRecord;
import org.weathertrack.api.service.forecast.openmeteo.model.ForecastReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ForecastDataConverter {
	ForecastDataConverter() {
	}

	public static ForecastData forecastReportToForecastData(ForecastReport forecastReportDTO) {
		if (forecastReportDTO == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.FORECAST_REPORT_DATA_IS_NULL);
		}

		ForecastData.Builder builder = new ForecastData.Builder();

		setTimeZone(forecastReportDTO);
		extractHourlyWeatherRecords(forecastReportDTO, builder);
		extractDailyWeatherRecords(forecastReportDTO, builder);
		extractUnits(forecastReportDTO, builder);

		return builder.build();
	}

	private static void extractUnits(ForecastReport forecastReportDTO, ForecastData.Builder builder) {
		forecastReportDTO.hourly_units().forEach((unit, value) -> builder.addUnit(Unit.fromJsonKey(unit), value));
		forecastReportDTO.daily_units().forEach((unit, value) -> builder.addUnit(Unit.fromJsonKey(unit), value));
	}

	private static void extractDailyWeatherRecords(ForecastReport forecastReportDTO, ForecastData.Builder builder) {
		List<String> dailyTimes = forecastReportDTO.daily().time();
		List<Double> dailyTemperatures = forecastReportDTO.daily().temperature_2m_max();
		List<Integer> dailyWeatherCodes = forecastReportDTO.daily().weathercode();
		List<Double> dailyWindSpeeds = forecastReportDTO.daily().windspeed_10m_max();
		List<Integer> dailyPrecipitations = forecastReportDTO.daily().precipitation_probability_max();

		for (int i = 0; i < dailyTimes.size(); i++) {
			LocalDate date = LocalDate.parse(dailyTimes.get(i), DateTimeFormatter.ISO_DATE);
			LocalDateTime dateTime = date.atStartOfDay();
			WeatherRecord dailyRecord = new WeatherRecord(
					dailyTemperatures.get(i),
					dailyWeatherCodes.get(i),
					dailyWindSpeeds.get(i),
					dailyPrecipitations.get(i)
			);
			builder.addDailyRecord(dateTime, dailyRecord);
		}
	}

	private static void extractHourlyWeatherRecords(ForecastReport forecastReportDTO, ForecastData.Builder builder) {
		List<String> hourlyTimes = forecastReportDTO.hourly().time();
		List<Double> hourlyTemperatures = forecastReportDTO.hourly().temperature_2m();
		List<Integer> hourlyWeatherCodes = forecastReportDTO.hourly().weathercode();
		List<Double> hourlyWindSpeeds = forecastReportDTO.hourly().windspeed_10m();
		List<Double> hourlyPrecipitations = forecastReportDTO.hourly().precipitation();
		List<Integer> hourlyHumidities = forecastReportDTO.hourly().relativehumidity_2m();

		for (int i = 0; i < hourlyTimes.size(); i++) {
			LocalDateTime dateTime = LocalDateTime.parse(hourlyTimes.get(i), DateTimeFormatter.ISO_DATE_TIME);
			WeatherRecord hourlyRecord = new WeatherRecord(
					hourlyTemperatures.get(i),
					hourlyWeatherCodes.get(i),
					hourlyWindSpeeds.get(i),
					hourlyPrecipitations.get(i).intValue(),
					hourlyHumidities.get(i)
			);
			builder.addHourlyRecord(dateTime, hourlyRecord);
		}
	}

	private static void setTimeZone(ForecastReport forecastReportDTO) {
		ZoneId zoneId = ZoneId.of(forecastReportDTO.timezone());
		ForecastData.Builder.setTimeZone(zoneId);
		ForecastData.Builder.setUtcOffsetSeconds(forecastReportDTO.utc_offset_seconds());
	}
}
