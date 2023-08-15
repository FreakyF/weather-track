package org.weathertrack.api.service.geocoding.openmeteo;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.net.URIBuilder;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.api.service.geocoding.GeocodingApiModule;
import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataResponseDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.GetCityDataRequest;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.resource.ApiMessageResource;
import org.weathertrack.api.service.resource.StatusCodesResource;
import org.weathertrack.geocoding.model.GeocodingCityData;
import org.weathertrack.model.Response;
import org.weathertrack.model.ResponseData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
	public ResponseData<List<CityDataDTO>> fetchCitiesForCityName(String cityName) throws IOException, InterruptedException, BadRequestException, NotFoundException {
		var validationResult = validateCityName(cityName);
		if (!validationResult.isSuccess()) {
			throw new IllegalArgumentException(validationResult.getMessage());
		}

		var response = getCitiesForCityNameFromApi(cityName);
		if (!response.isSuccess()) {
			return response;
		}
		var cityDataDTOS = response.getValue();

		if (cityDataDTOS == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL);
		}
		if (cityDataDTOS.isEmpty()) {
			return Response.fail(ApiMessageResource.NO_CITIES_FOUND);
		}

		return Response.ok(cityDataDTOS);
	}

	private ResponseData<List<CityDataDTO>> getCitiesForCityNameFromApi(String cityName) throws IOException, InterruptedException, BadRequestException, NotFoundException {
		URI requestUrl = buildGeocodingApiUri(cityName);
		var response = httpService.sendHttpGetRequest(requestUrl);

		if (response.statusCode() != HttpStatus.SC_OK) {
			return handleStatusCode(response.statusCode());
		}

		CityDataResponseDTO responseDTO = httpService.parseJsonResponse(response.body(), CityDataResponseDTO.class);
		return Response.ok(responseDTO.getResults());
	}

	@Override
	public GeocodingCityData fetchGeocodingDataForCity(GetCityDataRequest request) {
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

	private ResponseData<List<CityDataDTO>> handleStatusCode(int statusCode) throws BadRequestException, NotFoundException {
		return switch (statusCode) {
			case HttpStatus.SC_BAD_REQUEST -> throw new BadRequestException(ApiServiceExceptionMessage.STATUS_CODE_400);
			case HttpStatus.SC_NOT_FOUND -> throw new NotFoundException(ApiServiceExceptionMessage.STATUS_CODE_404);
			case HttpStatus.SC_TOO_MANY_REQUESTS -> Response.fail(StatusCodesResource.STATUS_CODE_429);
			case HttpStatus.SC_INTERNAL_SERVER_ERROR -> Response.fail(StatusCodesResource.STATUS_CODE_500);
			case HttpStatus.SC_SERVICE_UNAVAILABLE -> Response.fail(StatusCodesResource.STATUS_CODE_503);
			case HttpStatus.SC_GATEWAY_TIMEOUT -> Response.fail(StatusCodesResource.STATUS_CODE_504);
			default ->
					throw new UnsupportedOperationException(String.format(ApiServiceExceptionMessage.UNHANDLED_STATUS_CODE, statusCode));
		};
	}
}
