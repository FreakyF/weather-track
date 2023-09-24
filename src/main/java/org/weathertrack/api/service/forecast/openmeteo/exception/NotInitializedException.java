package org.weathertrack.api.service.forecast.openmeteo.exception;

public class NotInitializedException extends RuntimeException {
	public NotInitializedException(String message) {
		super(message);
	}
}
