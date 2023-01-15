package me.goudham.micronaut.trace.domain;

import me.goudham.micronaut.trace.service.LoggingService;
import me.goudham.micronaut.trace.shared.TimeInterpreter;
import me.goudham.micronaut.trace.shared.TraceInterpreter;

/**
 * Represents each type that's used for logging
 *
 * @see LoggingService
 * @see TraceInterpreter
 * @see TimeInterpreter
 */
public enum LogType {
    ENTERING,
    EXITING,
    ERROR,
    EXECUTION_TIME
}