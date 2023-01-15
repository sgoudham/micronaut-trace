package me.goudham.micronaut.trace.annotation;

import io.micronaut.aop.Around;
import jakarta.inject.Qualifier;
import me.goudham.micronaut.trace.shared.TimeInterpreter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @Time} calculates the execution time of the
 * annotated method and logs it.
 * <p></p>
 * <p>It is important to note that these logs will only be seen if {@link System.Logger}
 * is configured to log at {@link System.Logger.Level#TRACE}</p>
 *
 * @see TimeInterpreter
 */
@Qualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Around
public @interface Time {
}