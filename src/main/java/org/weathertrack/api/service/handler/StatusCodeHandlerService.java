package org.weathertrack.api.service.handler;

import org.weathertrack.api.service.exception.BadRequestException;
import org.weathertrack.api.service.exception.NotFoundException;
import org.weathertrack.model.ResponseData;

public interface StatusCodeHandlerService<V> {
	ResponseData<V> handleStatusCode(int statusCode) throws BadRequestException, NotFoundException;
}
