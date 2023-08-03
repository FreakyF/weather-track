package org.weathertrack.api.service.geocoding.openmeteo;

import com.google.inject.name.Named;
import org.apache.http.client.HttpClient;
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
import java.net.http.HttpResponse;
import java.util.List;

public class OpenMeteoGeocodingApiService implements GeocodingApiService {
	private final URIBuilder uriBuilder;
	private final HttpClient httpClient;
	private final HttpService httpService;

	public OpenMeteoGeocodingApiService(@Named(GeocodingApiModule.ANNOTATION_GEOCODING_API) URIBuilder uriBuilder, HttpClient httpClient, HttpService httpService) {
		this.uriBuilder = uriBuilder;
		this.httpClient = httpClient;
		this.httpService = httpService;
	}

	@Override
	public ResponseData<List<CityDataDTO>> fetchCitiesForCityName(String cityName) {
		validateCityName(cityName);
		var requestUrl = buildGeocodingApiUri(cityName);

		var response = fetchCityDataFromApi(requestUrl);
		if (response == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL);
		}

		return Response.ok(response);
	}

	@Override
	public ResponseData<CityDataDTO> fetchGeocodingDataForCity(GetCityDataRequest request) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	private void validateCityName(String cityName) {
		if (cityName == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.CITY_NAME_IS_NULL);
		}
		if (cityName.isBlank()) {
			throw new IllegalArgumentException(ApiServiceExceptionMessage.CITY_NAME_IS_BLANK);
		}
	}

	private URI buildGeocodingApiUri(String cityName) {
		try {
			return uriBuilder.setParameter("name", cityName).build();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(ApiServiceExceptionMessage.URI_SYNTAX_IS_INVALID);
		}
	}

	List<CityDataDTO> fetchCityDataFromApi(URI requestUrl) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	void validateHttpStatus(HttpResponse<InputStream> response) {
		throw new UnsupportedOperationException("Not Implemented");
	}
}
