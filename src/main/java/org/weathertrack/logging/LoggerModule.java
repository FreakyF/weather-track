package org.weathertrack.logging;

import com.google.inject.AbstractModule;

public class LoggerModule<T> extends AbstractModule {
	private final Class<T> type;

	public LoggerModule(Class<T> type) {
		this.type = type;
	}

	@Override
	protected void configure() {
		bind(Logger.class).toProvider(new LoggerProvider<>(type));
	}
}
