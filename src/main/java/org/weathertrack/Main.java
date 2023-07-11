package org.weathertrack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		for(int i = 0; i < 100; i++) {
			logger.trace("Example log from {}", Main.class.getSimpleName());
			logger.info("Example log from {}", Main.class.getSimpleName());
			logger.debug("Example log from {}", Main.class.getSimpleName());
			logger.warn("Example log from {}", Main.class.getSimpleName());
			logger.error("Example log from {}", Main.class.getSimpleName());
		}
	}
}
