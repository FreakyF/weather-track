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
import org.weathertrack.api.service.geocoding.model.GeocodingCityData;
import org.weathertrack.api.service.geocoding.openmeteo.model.CityDataResponseDTO;
import org.weathertrack.api.service.http.HttpService;
import org.weathertrack.api.service.http.exception.ParseJsonException;
import org.weathertrack.api.service.resource.ApiMessageResource;
import org.weathertrack.model.Response;
import org.weathertrack.model.ResponseData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.weathertrack.api.service.HttpStatusCodeHandler.handleStatusCode;

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
	public ResponseData<List<GeocodingCityData>> fetchCitiesForCityName(String cityName) throws IOException, InterruptedException, BadRequestException, NotFoundException {
		var validationResult = validateCityName(cityName);
		if (!validationResult.success()) {
			throw new IllegalArgumentException(validationResult.message());
		}

		var response = getCitiesForCityNameFromApi(cityName);
		if (!response.success()) {
			return response;
		}
		var cityDataDTOS = response.value();
		if (cityDataDTOS == null) {
			throw new NullPointerException(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL);
		}
		if (cityDataDTOS.isEmpty()) {
			return Response.fail(ApiMessageResource.NO_CITIES_FOUND);
		}

		return Response.ok(cityDataDTOS);
	}

	private ResponseData<List<GeocodingCityData>> getCitiesForCityNameFromApi(String cityName) throws IOException, InterruptedException, BadRequestException, NotFoundException {
		URI requestUrl = buildGeocodingApiUri(cityName);
		var response = httpService.sendHttpGetRequest(requestUrl);

		if (response.statusCode() != HttpStatus.SC_OK) {
			return handleStatusCode(response.statusCode());
		}

		CityDataResponseDTO responseDTO;
		try {
			responseDTO = httpService.parseJsonResponse(response.body(), CityDataResponseDTO.class);
			if (responseDTO.getResults() == null) {
				throw new NullPointerException(ApiServiceExceptionMessage.GEOCODING_CITY_DATA_IS_NULL);
			}
		} catch (ParseJsonException e) {
			return Response.fail("Could not get cities for city name: " + cityName);
		}
		List<GeocodingCityData> geocodingCitiesData = new ArrayList<>();
		for (var cityData : responseDTO.getResults()) {
			var geocodingCityData = new GeocodingCityData(
					cityData.name(),
					cityData.admin1(),
					cityData.country(),
					cityData.latitude(),
					cityData.longitude()
			);
			geocodingCitiesData.add(geocodingCityData);
		}

		return Response.ok(geocodingCitiesData);
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

}
