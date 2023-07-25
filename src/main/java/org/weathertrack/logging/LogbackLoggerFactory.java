package org.weathertrack.logging;

import com.google.inject.assistedinject.Assisted;

public interface LogbackLoggerFactory {
	<T> LogbackLogger<T> create(@Assisted Class<T> tClass);
}
