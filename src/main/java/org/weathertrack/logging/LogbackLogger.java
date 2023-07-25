package org.weathertrack.logging;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackLogger<T> extends BaseLogger<T> {
	private final Logger logger;

	@Inject
	public LogbackLogger(@Assisted Class<T> tClass) {
		super(tClass);
		logger = LoggerFactory.getLogger(tClass);
	}

	@Override
	public void info(String message) {
		var formattedMessage = formatMessage(message);
		logger.info(formattedMessage);
	}

	@Override
	public void warn(String message) {
		var formattedMessage = formatMessage(message);
		logger.warn(formattedMessage);
	}

	@Override
	public void error(String message) {
		var formattedMessage = formatMessage(message);
		logger.error(formattedMessage);
	}

	@Override
	public void info(String message, Throwable t) {
		var formattedMessage = formatMessage(message);
		logger.info(formattedMessage, t);
	}

	@Override
	public void warn(String message, Throwable t) {
		var formattedMessage = formatMessage(message);
		logger.warn(formattedMessage, t);
	}

	@Override
	public void error(String message, Throwable t) {
		var formattedMessage = formatMessage(message);
		logger.error(formattedMessage, t);
	}

	@Override
	public void info(String format, Object... args) {
		var formattedMessage = formatMessage(format);
		logger.info(formattedMessage, args);
	}

	@Override
	public void warn(String format, Object... args) {
		var formattedMessage = formatMessage(format);
		logger.warn(formattedMessage, args);
	}

	@Override
	public void error(String format, Object... args) {
		var formattedMessage = formatMessage(format);
		logger.error(formattedMessage, args);
	}
}
