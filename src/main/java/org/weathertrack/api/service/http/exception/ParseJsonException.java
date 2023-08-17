package org.weathertrack.api.service.http.exception;

public class ParseJsonException extends Exception {
	public ParseJsonException(String message) {
		super(message);
	}

	public ParseJsonException(String message, Exception innerException) {
		super(message, innerException);
	}
}
