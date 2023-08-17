package org.weathertrack.api.service.forecast.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ForecastData {
	private final Map<LocalDateTime, WeatherRecord> dailyWeatherRecords;
	private final Map<LocalDateTime, WeatherRecord> hourlyWeatherRecords;
	private final Map<Unit, String> units;
	private final ZoneId zoneId;
	private final int utcOffsetSeconds;

	public ForecastData(Builder builder) {
		this.dailyWeatherRecords = Collections.unmodifiableMap(builder.getDailyWeatherRecords());
		this.hourlyWeatherRecords = Collections.unmodifiableMap(builder.getHourlyWeatherRecords());
		this.units = Collections.unmodifiableMap(builder.getUnits());
		this.zoneId = builder.getZoneId();
		this.utcOffsetSeconds = builder.getUtcOffsetSeconds();
	}

	public static class Builder {
		private static final Map<LocalDateTime, WeatherRecord> dailyWeatherRecords = new HashMap<>();
		private static final Map<LocalDateTime, WeatherRecord> hourlyWeatherRecords = new HashMap<>();
		private static final Map<Unit, String> units = new HashMap<>();

		private static ZoneId zoneId;
		private static int utcOffsetSeconds;

		public Builder addDailyRecord(LocalDateTime date, WeatherRecord weatherRecord) {
			dailyWeatherRecords.put(date, weatherRecord);
			return this;
		}

		public Builder addHourlyRecord(LocalDateTime date, WeatherRecord weatherRecord) {
			hourlyWeatherRecords.put(date, weatherRecord);
			return this;
		}

		public Builder addUnit(Unit unit, String unitName) {
			units.put(unit, unitName);
			return this;
		}

		public Builder setUtcOffsetSeconds(int utcOffsetSeconds) {
			Builder.utcOffsetSeconds = utcOffsetSeconds;
			return this;
		}

		public ForecastData build() {
			return new ForecastData(this);
		}

		public ZoneId getZoneId() {
			return zoneId;
		}

		public int getUtcOffsetSeconds() {
			return utcOffsetSeconds;
		}

		public Map<LocalDateTime, WeatherRecord> getDailyWeatherRecords() {
			return dailyWeatherRecords;
		}

		public Map<LocalDateTime, WeatherRecord> getHourlyWeatherRecords() {
			return hourlyWeatherRecords;
		}

		public Map<Unit, String> getUnits() {
			return units;
		}

		public void setTimeZone(ZoneId zoneId) {
			Builder.zoneId = zoneId;
		}
	}

	public Map<LocalDateTime, WeatherRecord> getDailyWeatherRecords() {
		return dailyWeatherRecords;
	}

	public Map<LocalDateTime, WeatherRecord> getHourlyWeatherRecords() {
		return hourlyWeatherRecords;
	}

	public Map<Unit, String> getUnits() {
		return units;
	}

	public ZoneId getZoneId() {
		return zoneId;
	}

	public int getUtcOffsetSeconds() {
		return utcOffsetSeconds;
	}
}
