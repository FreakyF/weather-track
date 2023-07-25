package org.weathertrack.logging;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class LoggingModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(LogbackLoggerFactory.class).to(BaseLogbackLoggerFactory.class);
		install(new FactoryModuleBuilder()
				.implement(new TypeLiteral<Logger<?>>() {
				}, new TypeLiteral<LogbackLogger<?>>() {
				})
				.build(LogbackLoggerFactory.class));
	}
}
