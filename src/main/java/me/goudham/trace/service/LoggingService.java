package me.goudham.trace.service;

import jakarta.inject.Singleton;
import me.goudham.trace.domain.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encapsulates the built-in {@link Logger}
 */
@Singleton
public class LoggingService {
    private Logger logger;

    public void trace(LogType logType, String method, String signature) {
        logger.trace("[{}]: {}({})", logType.name(), method, signature);
    }

    public void trace(LogType logType, String method, String signature, long timeElapsed) {
        logger.trace("[{}]: Elapsed execution time for {}({}) is {} milliseconds", logType.name(), method, signature, timeElapsed);
    }

    public void setLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }
}