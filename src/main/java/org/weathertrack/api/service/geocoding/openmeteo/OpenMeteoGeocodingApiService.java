package org.weathertrack.api.service.geocoding.openmeteo;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.http.client.utils.URIBuilder;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.geocoding.GeocodingApiModule;
import org.weathertrack.api.service.geocoding.GeocodingApiService;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataDTO;
import org.weathertrack.api.service.geocoding.openmeteo.model.GetCityDataRequest;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.model.Response;
import org.weathertrack.model.ResponseData;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
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
		throw new UnsupportedOperationException("Not Implemented");
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
