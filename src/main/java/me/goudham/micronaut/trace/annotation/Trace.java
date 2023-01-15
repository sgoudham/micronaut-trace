package me.goudham.micronaut.trace.annotation;

import io.micronaut.aop.Around;
import jakarta.inject.Qualifier;
import me.goudham.micronaut.trace.shared.TraceInterpreter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @Trace} indicates that the current method will be monitored to allow
 * logging when the method was entered and exited.
 * <p></p>
 * <p>It is important to note that these logs will only be seen if {@link System.Logger}
 * is configured to log at {@link System.Logger.Level#TRACE}</p>
 *
 * @see TraceInterpreter
 */
@Qualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Around
public @interface Trace {
}