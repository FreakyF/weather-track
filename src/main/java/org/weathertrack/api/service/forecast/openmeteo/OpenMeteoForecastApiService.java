package org.weathertrack.api.service.forecast.openmeteo;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.net.URIBuilder;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.api.service.forecast.ForecastApiModule;
import org.weathertrack.api.service.forecast.ForecastApiService;
import org.weathertrack.api.service.forecast.model.ForecastData;
import org.weathertrack.api.service.forecast.openmeteo.builder.OpenMeteoApiURIBuilder;
import org.weathertrack.api.service.forecast.openmeteo.converter.ForecastDataConverter;
import org.weathertrack.api.service.forecast.openmeteo.model.ForecastReport;
import org.weathertrack.api.service.forecast.openmeteo.resource.OpenMeteoForecastApiServiceResource;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.http.exception.ParseJsonException;
import org.weathertrack.model.Response;
import org.weathertrack.model.ResponseData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.weathertrack.api.service.HttpStatusCodeHandler.handleStatusCode;

public class OpenMeteoForecastApiService implements ForecastApiService {
	private final URIBuilder uriBuilder;
	private final HttpService httpService;

	@Inject
	public OpenMeteoForecastApiService(
			@Named(ForecastApiModule.ANNOTATION_FORECAST_API) URIBuilder uriBuilder,
			HttpService httpService) {
		this.uriBuilder = uriBuilder;
		this.httpService = httpService;
	}

	@Override
	public ResponseData<ForecastData> fetchForecastForCoordinates(GeocodingCityData geocodingCityData)
			throws BadRequestException, NotFoundException, IOException, InterruptedException {
		if (geocodingCityData == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL);
		}

		var response = getForecastForCoordinatesFromApi(geocodingCityData);
		if (!response.success()) {
			return Response.fail(response.message());
		}
		var forecastReportDTO = response.value();
		if (forecastReportDTO == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.FORECAST_REPORT_DATA_IS_NULL);
		}

		var forecastData = ForecastDataConverter.forecastReportToForecastData(forecastReportDTO);
		return Response.ok(forecastData);
	}

	private ResponseData<ForecastReport> getForecastForCoordinatesFromApi(GeocodingCityData geocodingCityData) throws BadRequestException, NotFoundException, IOException, InterruptedException {
		var requestUrl = buildForecastApiUri(geocodingCityData);
		var response = httpService.sendHttpGetRequest(requestUrl);

		if (response.statusCode() != HttpStatus.SC_OK) {
			return handleStatusCode(response.statusCode());
		}

		ForecastReport responseDTO;
		try {
			responseDTO = httpService.parseJsonResponse(response.body(), ForecastReport.class);
		} catch (ParseJsonException e) {
			return Response.fail(OpenMeteoForecastApiServiceResource.COULD_NOT_GET_FORECAST_FOR_COORDINATES + geocodingCityData.longitude() + ", " + geocodingCityData.latitude());
		}

		return Response.ok(responseDTO);
	}

	private URI buildForecastApiUri(GeocodingCityData geocodingCityData) {
		var latitude = geocodingCityData.latitude();
		var longitude = geocodingCityData.longitude();

		try {
			return new OpenMeteoApiURIBuilder.Builder(uriBuilder)
					.setLatitude(latitude)
					.setLongitude(longitude)
					.includeHourlyTemperature()
					.includeHourlyRelativeHumidity()
					.includeHourlyPrecipitation()
					.includeHourlyWeatherCode()
					.includeHourlySurfacePressure()
					.includeHourlyWindSpeed()
					.includeDailyMaxTemperature()
					.includeDailyMaxPrecipitation()
					.includeDailyWeatherCode()
					.includeDailyMaxWindSpeed()
					.includeAutoTimeZone()
					.build();
		} catch (URISyntaxException e) {
			throw new RuntimeException(ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID);
		}
	}
}
