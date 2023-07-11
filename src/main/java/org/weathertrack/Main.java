package org.weathertrack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		String logInfo = "Example log from {}";
		for(int i = 0; i < 10000; i++) {
			logger.trace(logInfo, Main.class.getSimpleName());
			logger.info(logInfo, Main.class.getSimpleName());
			logger.debug(logInfo, Main.class.getSimpleName());
			logger.warn(logInfo, Main.class.getSimpleName());
			logger.error(logInfo, Main.class.getSimpleName());
		}
	}
}
