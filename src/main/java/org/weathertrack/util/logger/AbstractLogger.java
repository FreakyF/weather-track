package org.weathertrack.util.logger;

public abstract class AbstractLogger<T> implements LoggerInterface<T> {
	protected final String className;

	protected AbstractLogger(Class<T> className) {
		this.className = className.getSimpleName();
	}

	protected String formatMessage(String message) {
		return String.format("[%s] %s", className, message);
	}
}
