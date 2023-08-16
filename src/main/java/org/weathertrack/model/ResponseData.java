package org.weathertrack.model;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ResponseData<?> that)) return false;
		return isSuccess() == that.isSuccess() && Objects.equals(getMessage(), that.getMessage()) && Objects.equals(getValue(), that.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(isSuccess(), getMessage(), getValue());
	}
}
