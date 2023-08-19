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

		setTimeZone(forecastReportDTO, builder);
		extractHourlyWeatherRecords(forecastReportDTO, builder);
		extractDailyWeatherRecords(forecastReportDTO, builder);
		extractUnits(forecastReportDTO, builder);

		return builder.build();
	}

	private static void extractUnits(ForecastReport forecastReportDTO, ForecastData.Builder builder) {
		forecastReportDTO.getHourly_units().forEach((unit, value) -> builder.addUnit(Unit.fromJsonKey(unit), value));
		forecastReportDTO.getDaily_units().forEach((unit, value) -> builder.addUnit(Unit.fromJsonKey(unit), value));
	}

	private static void extractDailyWeatherRecords(ForecastReport forecastReportDTO, ForecastData.Builder builder) {
		List<String> dailyTimes = forecastReportDTO.getDaily().time();
		List<Double> dailyTemperatures = forecastReportDTO.getDaily().temperature_2m_max();
		List<Integer> dailyWeatherCodes = forecastReportDTO.getDaily().weathercode();
		List<Double> dailyWindSpeeds = forecastReportDTO.getDaily().windspeed_10m_max();
		List<Integer> dailyPrecipitations = forecastReportDTO.getDaily().precipitation_probability_max();

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
		List<String> hourlyTimes = forecastReportDTO.getHourly().getTime();
		List<Double> hourlyTemperatures = forecastReportDTO.getHourly().getTemperature_2m();
		List<Integer> hourlyWeatherCodes = forecastReportDTO.getHourly().getWeathercode();
		List<Double> hourlyWindSpeeds = forecastReportDTO.getHourly().getWindspeed_10m();
		List<Double> hourlyPrecipitations = forecastReportDTO.getHourly().getPrecipitation();
		List<Integer> hourlyHumidities = forecastReportDTO.getHourly().getRelativehumidity_2m();

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

	private static void setTimeZone(ForecastReport forecastReportDTO, ForecastData.Builder builder) {
		ZoneId zoneId = ZoneId.of(forecastReportDTO.getTimezone());
		builder.setTimeZone(zoneId);
		builder.setUtcOffsetSeconds(forecastReportDTO.getUtc_offset_seconds());
	}
}
