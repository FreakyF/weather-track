package org.weathertrack.api.service.uri.builder.openmeteo;

import org.apache.hc.core5.net.URIBuilder;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.forecast.openmeteo.resource.ApiParametersResource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OpenMeteoApiParameterBuilder {
	Map<String, String> parameters;

	public OpenMeteoApiParameterBuilder(Builder builder) {
		parameters = Collections.unmodifiableMap(builder.getParameters());
	}

	public static class Builder {
		private final Map<String, String> parameters = new HashMap<>();

		public OpenMeteoApiParameterBuilder build() {
			return new OpenMeteoApiParameterBuilder(this);
		}

		public URI buildURI(URIBuilder uriBuilder) {
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				uriBuilder.addParameter(entry.getKey(), entry.getValue());
			}

			try {
				return uriBuilder.build();
			} catch (URISyntaxException e) {
				throw new IllegalArgumentException(ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID);
			}
		}

		public Builder setLatitude(double latitude) {
			parameters.put(ApiParametersResource.LATITUDE, String.valueOf(latitude));
			return this;
		}

		public Builder setLongitude(double longitude) {
			parameters.put(ApiParametersResource.LONGITUDE, String.valueOf(longitude));
			return this;
		}

		public Builder setHourlyTemperature() {
			parameters.put(ApiParametersResource.HOURLY, "temperature_2m");
			return this;
		}

		public Builder setHourlyRelativeHumidity() {
			parameters.put(ApiParametersResource.HOURLY, "relativehumidity_2m");
			return this;
		}

		public Builder setHourlyPrecipitation() {
			parameters.put(ApiParametersResource.HOURLY, "precipitation");
			return this;
		}

		public Builder setHourlyWeatherCode() {
			parameters.put(ApiParametersResource.HOURLY, "weathercode");
			return this;
		}

		public Builder setHourlySurfacePressure() {
			parameters.put(ApiParametersResource.HOURLY, "surface_pressure");
			return this;
		}

		public Builder setHourlyWindSpeed() {
			parameters.put(ApiParametersResource.HOURLY, "windspeed_10m");
			return this;
		}

		public Builder setDailyMaxTemperature() {
			parameters.put(ApiParametersResource.DAILY, "temperature_2m_max");
			return this;
		}

		public Builder setDailyWeatherCode() {
			parameters.put(ApiParametersResource.DAILY, "weathercode");
			return this;
		}

		public Builder setDailyMaxPrecipitation() {
			parameters.put(ApiParametersResource.DAILY, "precipitation_probability_max");
			return this;
		}

		public Builder setDailyMaxWindSpeed() {
			parameters.put(ApiParametersResource.DAILY, "windspeed_10m_max");
			return this;
		}

		public Builder setAutoTimeZone() {
			parameters.put(ApiParametersResource.TIMEZONE, "auto");
			return this;
		}

		public Map<String, String> getParameters() {
			return parameters;
		}
	}
}
