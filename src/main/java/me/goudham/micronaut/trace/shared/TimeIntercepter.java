package me.goudham.micronaut.trace.shared;

import io.micronaut.aop.MethodInvocationContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import me.goudham.micronaut.trace.annotation.Time;
import me.goudham.micronaut.trace.domain.LogType;
import me.goudham.micronaut.trace.service.LoggingService;

import java.util.concurrent.TimeUnit;

/**
 * Intercepts the annotated method and logs out the
 * execution time of the entire method.
 *
 * @see Time
 */
@Singleton
class TimeIntercepter extends Intercepter {

    @Inject
    TimeIntercepter(LoggingService loggingService) {
        super(loggingService);
    }

    /**
     * Logs the execution time of the annotated method.
     * The method name and signature are included within
     * the log itself.
     *
     * @param context Object representing information about the annotated method
     * @return Result of annotated method after execution
     */
    @Override
    Object logAndExecuteMethod(MethodInvocationContext<Object, Object> context) {
        long start = System.nanoTime();
        Object result = context.proceed();
        long finish = System.nanoTime();
        long timeElapsed = TimeUnit.NANOSECONDS.toMillis(finish - start);

        loggingService.trace(LogType.EXECUTION_TIME, context.getMethodName(), getArguments(context), timeElapsed);

        return result;
    }
}