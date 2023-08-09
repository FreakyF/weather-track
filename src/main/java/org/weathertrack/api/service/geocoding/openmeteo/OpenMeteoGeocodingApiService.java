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
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class OpenMeteoGeocodingApiService implements GeocodingApiService {
	private final URIBuilder uriBuilder;
	private final HttpClient httpClient;
	private final HttpService httpService;

	@Inject
	public OpenMeteoGeocodingApiService(@Named(GeocodingApiModule.ANNOTATION_GEOCODING_API) URIBuilder uriBuilder, HttpClient httpClient, HttpService httpService) {
		this.uriBuilder = uriBuilder;
		this.httpClient = httpClient;
		this.httpService = httpService;
	}

	@Override
	public ResponseData<List<CityDataDTO>> fetchCitiesForCityName(String cityName) throws IllegalArgumentException {
		var validationResult = validateCityName(cityName);
		if (!validationResult.isSuccess()) {
			throw new IllegalArgumentException(validationResult.getMessage());
		}
		var requestUrl = buildGeocodingApiUri(cityName);
		try {
			var response = httpService.sendHttpGetRequest(requestUrl);
			if (response.statusCode() == HttpStatus.SC_OK) {
				CityDataResponseDTO responseDTO = httpService.parseJsonResponse(response.body(), CityDataResponseDTO.class);
				if (responseDTO.getResults() == null) {
					throw new NullPointerException(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL);
				}
				if (responseDTO.getResults().length == 0) {
					return Response.fail(ApiMessageResource.NO_CITIES_FOUND);
				}
				var responseFinal = Arrays.asList(responseDTO.getResults());
				return Response.ok(responseFinal);
			}
			if (response.statusCode() == HttpStatus.SC_BAD_REQUEST) {
				throw new IllegalArgumentException(ApiServiceExceptionMessage.STATUS_CODE_400);
			}
			if (response.statusCode() == HttpStatus.SC_NOT_FOUND) {
				throw new IllegalArgumentException(ApiServiceExceptionMessage.STATUS_CODE_404);
			}
			if (response.statusCode() == HttpStatus.SC_TOO_MANY_REQUESTS) {
				return Response.fail(StatusCodesResource.STATUS_CODE_429);
			}
			if (response.statusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				return Response.fail(StatusCodesResource.STATUS_CODE_500);
			}
			if (response.statusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE) {
				return Response.fail(StatusCodesResource.STATUS_CODE_503);
			}
			if (response.statusCode() == HttpStatus.SC_GATEWAY_TIMEOUT) {
				return Response.fail(StatusCodesResource.STATUS_CODE_504);
			}
			return null;
		} catch (IOException | InterruptedException e) {
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

	List<CityDataDTO> fetchCityDataFromApiResponse(URI requestUrl) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	String HandleStatusCode(HttpResponse<InputStream> response) {
		throw new UnsupportedOperationException("Not Implemented");
	}
}
