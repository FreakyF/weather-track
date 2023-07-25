package org.weathertrack.input.service.userio;

import com.google.inject.AbstractModule;
import org.weathertrack.logging.LogbackLogger;
import org.weathertrack.logging.Logger;

import java.util.Scanner;

public class UserIOModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Scanner.class).toInstance(new Scanner(System.in));
		bind(UserIOService.class).to(CommandLineUserIOService.class);
		bind(Logger.class).to(LogbackLogger.class);
	}
}
