package org.weathertrack.logging;

public class BaseLogbackLoggerFactory implements LogbackLoggerFactory {
	@Override
	public <T> LogbackLogger<T> create(Class<T> tClass) {
		return new LogbackLogger<>(tClass);
	}
}
