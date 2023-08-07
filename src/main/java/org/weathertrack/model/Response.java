package org.weathertrack.model;

public class Response {
	private Response() {
	}

	public static <V> ResponseData<V> ok(V value) {
		return new ResponseData<>(true, null, value);
	}

	public static <V> ResponseData<V> ok() {
		return new ResponseData<>(true, null, null);
	}

	public static <V> ResponseData<V> fail(String message) {
		return new ResponseData<>(false, message, null);
	}
}
