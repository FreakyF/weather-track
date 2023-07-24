package org.weathertrack.input.service.userio;

import com.google.inject.AbstractModule;
import org.weathertrack.logging.LogbackLogger;
import org.weathertrack.logging.Logger;

import java.util.Scanner;

public class UserIOServiceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Logger.class).toInstance(new LogbackLogger<>(CommandLineUserIOService.class));
		bind(Scanner.class).toInstance(new Scanner(System.in));
	}
}
