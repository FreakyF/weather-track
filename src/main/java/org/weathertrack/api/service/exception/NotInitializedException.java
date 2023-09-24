package org.weathertrack.api.service.exception;

public class NotInitializedException extends RuntimeException {
	public NotInitializedException(String message) {
		super(message);
	}
}
