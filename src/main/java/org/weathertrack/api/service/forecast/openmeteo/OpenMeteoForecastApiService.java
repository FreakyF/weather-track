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
import org.weathertrack.api.service.forecast.openmeteo.model.WeatherReport;
import org.weathertrack.api.service.forecast.openmeteo.model.WeatherReportResponseDTO;
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.http.HttpService;
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
	public ResponseData<WeatherReport> fetchForecastForCoordinates(GeocodingCityData geocodingCityData) throws BadRequestException, NotFoundException, IOException, InterruptedException {
		if (geocodingCityData == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL);
		}

		var response = getForecastForCoordinatesFromApi(geocodingCityData);
		if (!response.isSuccess()) {
			return response;
		}
		var forecastReportDTO = response.getValue();
		if (forecastReportDTO == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.FORECAST_REPORT_DATA_IS_NULL);
		}
		return Response.ok(forecastReportDTO);
	}

	private ResponseData<WeatherReport> getForecastForCoordinatesFromApi(GeocodingCityData geocodingCityData) throws BadRequestException, NotFoundException, IOException, InterruptedException {
		URI requestUrl = buildForecastApiUri(geocodingCityData);
		var response = httpService.sendHttpGetRequest(requestUrl);

		if (response.statusCode() != HttpStatus.SC_OK) {
			return handleStatusCode(response.statusCode());
		}

		WeatherReportResponseDTO responseDTO = httpService.parseJsonResponse(response.body(), WeatherReportResponseDTO.class);
		return Response.ok(responseDTO.getResults());
	}

	private URI buildForecastApiUri(GeocodingCityData geocodingCityData) {
		var latitude = geocodingCityData.latitude();
		var latitudeString = String.valueOf(latitude);

		var longitude = geocodingCityData.longitude();
		var longitudeString = String.valueOf(longitude);
		try {
			return uriBuilder
					.setParameter("latitude", latitudeString)
					.setParameter("longitude", longitudeString)
					.setParameter("hourly", "temperature_2m")
					.setParameter("daily", "weathercode,temperature_2m_max,windspeed_10m_max,winddirection_10m_dominant")
					.setParameter("timezone", "GMT")
					.build();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID);
		}
	}
}
