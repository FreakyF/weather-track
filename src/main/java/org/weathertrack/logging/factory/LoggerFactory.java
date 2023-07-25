package org.weathertrack.logging.factory;

import org.weathertrack.logging.Logger;

public interface LoggerFactory {
	<T> Logger<T> create(Class<T> tClass);
}
