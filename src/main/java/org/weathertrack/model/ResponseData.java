package org.weathertrack.model;

public class ResponseData<V> {
	private final boolean success;
	private final String message;
	private final V value;

	public ResponseData(boolean success, String message, V value) {
		this.success = success;
		this.message = message;
		this.value = value;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public V getValue() {
		return value;
	}
}
