package org.weathertrack.util.logger;

public interface LoggerInterface<T> {
	void info(String message);

	void warn(String message);

	void error(String message);

	void info(String message, Throwable t);

	void warn(String message, Throwable t);

	void error(String message, Throwable t);

	void info(String format, Object... args);

	void warn(String format, Object... args);

	void error(String format, Object... args);
}
