package org.weathertrack.logging;

import com.google.inject.assistedinject.Assisted;

public interface LogbackLoggerFactory {
	LogbackLogger create(@Assisted Class<?> clazz);
}
