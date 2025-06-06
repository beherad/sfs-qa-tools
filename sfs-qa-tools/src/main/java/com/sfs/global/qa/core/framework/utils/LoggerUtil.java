package com.sfs.global.qa.core.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerUtil {

	private final Logger logger;

    public LoggerUtil(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void info(String message) {
        logger.info(addPrefix(message));
    }

    public void debug(String message) {
        logger.debug(addPrefix(message));
    }

    public void error(String message) {
        logger.error(addPrefix(message));
    }

    private String addPrefix(String message) {
        // Customize this method for standard log formatting
        return "[CUSTOM-LOG] " + message;
    }
}
