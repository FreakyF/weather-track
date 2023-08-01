package org.weathertrack.model;

public class Response {
	private Response() {
	}

	public static <V> ResponseData ok(V value) {
		return new ResponseData<V>(true, null, value);
	}

	public static <V> ResponseData error(String message) {
		return new ResponseData<V>(true, message, null);
	}
}
