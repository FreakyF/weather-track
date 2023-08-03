package org.weathertrack.model;

public class Response {
	private Response() {
	}

	public static <V> ResponseData<V> ok(V value) {
		return new ResponseData<>(true, null, value);
	}

	public static <V> ResponseData<V> error(String message) {
		return new ResponseData<>(true, message, null);
	}
}
