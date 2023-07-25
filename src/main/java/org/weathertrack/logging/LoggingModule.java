package org.weathertrack.logging;

import com.google.inject.AbstractModule;
import org.weathertrack.logging.factory.LogbackLoggerFactory;
import org.weathertrack.logging.factory.LoggerFactory;

public class LoggingModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(LoggerFactory.class).to(LogbackLoggerFactory.class);
	}
}
