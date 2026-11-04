package com.anhtester.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** BT14: Ghi log với Log4j2 (config: src/test/resources/log4j2.properties) */
public class LogUtils {
    private static final Logger LOGGER = LogManager.getLogger(LogUtils.class);

    public static void info(Object message) {
        LOGGER.info(message);
    }

    public static void warn(Object message) {
        LOGGER.warn(message);
    }

    public static void error(Object message) {
        LOGGER.error(message);
    }

    public static void debug(Object message) {
        LOGGER.debug(message);
    }
}
