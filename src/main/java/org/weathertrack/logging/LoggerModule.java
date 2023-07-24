package org.weathertrack.logging;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.weathertrack.input.service.userio.CommandLineUserIOService;

public class LoggerModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(new TypeLiteral<Logger<CommandLineUserIOService>>() {}).toProvider(() -> new LogbackLogger<>(CommandLineUserIOService.class));
	}
}
