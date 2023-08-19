package org.weathertrack.model;

import java.util.Objects;

public record ResponseData<V>(boolean success, String message, V value) {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ResponseData<?> that)) return false;
		return success() == that.success() && Objects.equals(message(), that.message()) && Objects.equals(value(), that.value());
	}

	@Override
	public int hashCode() {
		return Objects.hash(success(), message(), value());
	}
}
