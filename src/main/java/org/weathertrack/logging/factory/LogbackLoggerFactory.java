package org.weathertrack.logging.factory;

import org.weathertrack.logging.Logger;
import org.weathertrack.logging.logger.LogbackLogger;

public class LogbackLoggerFactory implements LoggerFactory {
	@Override
	public <T> Logger<T> create(Class<T> tClass) {
		return new LogbackLogger<>(tClass);
	}
}
