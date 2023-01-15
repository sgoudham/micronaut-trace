package me.goudham.trace.domain;

import me.goudham.trace.shared.TimeInterpreter;
import me.goudham.trace.shared.TraceInterpreter;
import me.goudham.trace.service.LoggingService;

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