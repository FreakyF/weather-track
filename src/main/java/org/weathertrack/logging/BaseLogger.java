package org.weathertrack.logging;

public abstract class BaseLogger<T> implements Logger<T> {
	protected final String className;

	protected BaseLogger(Class<T> className) {
		this.className = className.getSimpleName();
	}

	protected String formatMessage(String message) {
		return String.format("[%s] %s", className, message);
	}
}
