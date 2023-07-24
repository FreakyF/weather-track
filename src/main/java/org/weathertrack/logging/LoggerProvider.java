package org.weathertrack.logging;

import com.google.inject.Inject;
import jakarta.inject.Provider;

class LoggerProvider<T> implements Provider<Logger> {
	private final Class<T> type;

	@Inject
	public LoggerProvider(Class<T> type) {
		this.type = type;
	}

	@Override
	public Logger get() {
		return new LogbackLogger<>(type);
	}
}
