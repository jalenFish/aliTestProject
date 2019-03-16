package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yjl on 2019-01-22.
 */
public class LogbackTest {
    private final static Logger logger = LoggerFactory.getLogger(LogbackTest.class);

    public static void main(String[] args) {
        logger.debug("logback debug");
        logger.info("logback info");
        logger.error("logback error");
    }

}
