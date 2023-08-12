package org.weathertrack.api.service.geocoding.openmeteo;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.net.URIBuilder;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.geocoding.GeocodingApiModule;
import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataResponseDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.GetCityDataRequest;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.resource.ApiMessageResource;
import org.weathertrack.api.service.resource.StatusCodesResource;
import org.weathertrack.model.Response;
import org.weathertrack.model.ResponseData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class OpenMeteoGeocodingApiService implements GeocodingApiService {
	private final URIBuilder uriBuilder;
	private final HttpService httpService;

	@Inject
	public OpenMeteoGeocodingApiService(
			@Named(GeocodingApiModule.ANNOTATION_GEOCODING_API) URIBuilder uriBuilder,
			HttpService httpService) {
		this.uriBuilder = uriBuilder;
		this.httpService = httpService;
	}

	@Override
	public ResponseData<List<CityDataDTO>> fetchCitiesForCityName(String cityName) {
		var validationResult = validateCityName(cityName);
		if (!validationResult.isSuccess()) {
			throw new IllegalArgumentException(validationResult.getMessage());
		}

		URI requestUrl = buildGeocodingApiUri(cityName);
		try {
			var response = httpService.sendHttpGetRequest(requestUrl);
			int statusCode = response.statusCode();

			if (statusCode == HttpStatus.SC_OK) {
				CityDataResponseDTO responseDTO = httpService.parseJsonResponse(response.body(), CityDataResponseDTO.class);
				CityDataDTO[] cityDataDTO = responseDTO.getResults();

				if (cityDataDTO == null) {
					throw new NullPointerException(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL);
				}
				if (cityDataDTO.length == 0) {
					return Response.fail(ApiMessageResource.NO_CITIES_FOUND);
				}

				return Response.ok(Arrays.asList(cityDataDTO));
			} else {
				return handleStatusCode(statusCode);
			}
		} catch (IOException | InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResponseData<CityDataDTO> fetchGeocodingDataForCity(GetCityDataRequest request) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	private ResponseData<?> validateCityName(String cityName) {
		if (cityName == null) {
			return Response.fail(ApiServiceExceptionMessage.CITY_NAME_IS_NULL);
		}
		if (cityName.isBlank()) {
			return Response.fail(ApiServiceExceptionMessage.CITY_NAME_IS_BLANK);
		}
		return Response.ok();
	}

	private URI buildGeocodingApiUri(String cityName) {
		try {
			return uriBuilder.setParameter("name", cityName).build();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID);
		}
	}

	private ResponseData<List<CityDataDTO>> handleStatusCode(int statusCode) {
		return switch (statusCode) {
			case HttpStatus.SC_BAD_REQUEST ->
					throw new IllegalArgumentException(ApiServiceExceptionMessage.STATUS_CODE_400);
			case HttpStatus.SC_NOT_FOUND ->
					throw new IllegalArgumentException(ApiServiceExceptionMessage.STATUS_CODE_404);
			case HttpStatus.SC_TOO_MANY_REQUESTS -> Response.fail(StatusCodesResource.STATUS_CODE_429);
			case HttpStatus.SC_INTERNAL_SERVER_ERROR -> Response.fail(StatusCodesResource.STATUS_CODE_500);
			case HttpStatus.SC_SERVICE_UNAVAILABLE -> Response.fail(StatusCodesResource.STATUS_CODE_503);
			case HttpStatus.SC_GATEWAY_TIMEOUT -> Response.fail(StatusCodesResource.STATUS_CODE_504);
			default -> null;
		};
	}
}
