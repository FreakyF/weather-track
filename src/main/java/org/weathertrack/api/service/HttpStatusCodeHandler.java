package org.weathertrack.api.service;

import org.apache.hc.core5.http.HttpStatus;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;
import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.api.service.resource.StatusCodesResource;
import org.weathertrack.model.Response;
import org.weathertrack.model.ResponseData;

public class HttpStatusCodeHandler {
	public static <V> ResponseData<V> handleStatusCode(int statusCode) throws BadRequestException, NotFoundException {
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

	private HttpStatusCodeHandler() {
	}
}
