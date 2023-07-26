package org.weathertrack;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.weathertrack.input.service.userio.UserIOModule;
import org.weathertrack.logging.LoggingModule;

public class Main {
	private static final Injector injector = Guice.createInjector(new LoggingModule(), new UserIOModule());

	public static void main(String[] args) {
	}
}
