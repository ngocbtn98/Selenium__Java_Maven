package com.anhtester.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BÀI TẬP 14: Ghi log ra console + file bằng Log4j2 (config: src/test/resources/log4j2.properties).
 */
public class LogUtils {
    private static final Logger logger = LogManager.getLogger(LogUtils.class);

    public static void info(Object message) {
        logger.info(String.valueOf(message));
    }

    public static void warn(Object message) {
        logger.warn(String.valueOf(message));
    }

    public static void error(Object message) {
        logger.error(String.valueOf(message));
    }

    public static void debug(Object message) {
        logger.debug(String.valueOf(message));
    }
}
