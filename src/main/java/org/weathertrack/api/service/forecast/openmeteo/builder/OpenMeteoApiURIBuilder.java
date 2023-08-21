package org.weathertrack.api.service.forecast.openmeteo.builder;

import org.apache.hc.core5.net.URIBuilder;
import org.weathertrack.type.KeyValues;
import org.weathertrack.type.KeyValuesPair;

import java.net.URI;
import java.net.URISyntaxException;

public class OpenMeteoApiURIBuilder {
	KeyValuesPair<String, String> parameters;

	public OpenMeteoApiURIBuilder() {
		parameters = new KeyValues<>();
	}

	public static class Builder {
		private final KeyValuesPair<String, String> parameters = new KeyValues<>();
		private final URIBuilder uriBuilder;

		public Builder(URIBuilder uriBuilder) {
			this.uriBuilder = uriBuilder;
		}

		public URI build() throws URISyntaxException {
			for (String key : parameters.getKeys()) {
				var values = parameters.get(key);
				var parameterValue = String.join(",", values);
				uriBuilder.setParameter(key, parameterValue);
			}

			return uriBuilder.build();
		}

		public Builder setLatitude(double latitude) {
			parameters.put(ApiParameters.LATITUDE, String.valueOf(latitude));
			return this;
		}

		public Builder setLongitude(double longitude) {
			parameters.put(ApiParameters.LONGITUDE, String.valueOf(longitude));
			return this;
		}

		public Builder includeHourlyTemperature() {
			setParameter(ApiParameters.HOURLY, "temperature_2m");
			return this;
		}

		public Builder includeHourlyRelativeHumidity() {
			setParameter(ApiParameters.HOURLY, "relativehumidity_2m");
			return this;
		}

		public Builder includeHourlyPrecipitation() {
			setParameter(ApiParameters.HOURLY, "precipitation");
			return this;
		}

		public Builder includeHourlyWeatherCode() {
			setParameter(ApiParameters.HOURLY, "weathercode");
			return this;
		}

		public Builder includeHourlySurfacePressure() {
			setParameter(ApiParameters.HOURLY, "surface_pressure");
			return this;
		}

		public Builder includeHourlyWindSpeed() {
			setParameter(ApiParameters.HOURLY, "windspeed_10m");
			return this;
		}

		public Builder includeDailyMaxTemperature() {
			setParameter(ApiParameters.DAILY, "temperature_2m_max");
			return this;
		}

		public Builder includeDailyWeatherCode() {
			setParameter(ApiParameters.DAILY, "weathercode");
			return this;
		}

		public Builder includeDailyMaxPrecipitation() {
			setParameter(ApiParameters.DAILY, "precipitation_probability_max");
			return this;
		}

		public Builder includeDailyMaxWindSpeed() {
			setParameter(ApiParameters.DAILY, "windspeed_10m_max");
			return this;
		}

		public Builder includeAutoTimeZone() {
			setParameter(ApiParameters.TIMEZONE, "auto");
			return this;
		}

		private void setParameter(String parameterName, String value) {
			parameters.put(parameterName, value);
		}
	}
}
