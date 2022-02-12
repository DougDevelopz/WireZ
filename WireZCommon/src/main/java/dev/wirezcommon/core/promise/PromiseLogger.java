package dev.wirezcommon.core.promise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PromiseLogger {

    private static final Logger PROMISE_LOGGER = LoggerFactory.getLogger(PromiseSupport.class);

    public static void log(String logged, Object o) {
        PROMISE_LOGGER.warn(logged, o);
    }
}
