package org.weathertrack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.weathertrack.input.service.userio.UserIOService;
import org.weathertrack.logging.LoggingModule;

public class Main {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new LoggingModule());
		var userIOService = injector.getInstance(UserIOService.class);
		userIOService.getUserInput("Test: ");
	}
}
