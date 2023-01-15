package me.goudham.micronaut.trace.shared;

import io.micronaut.aop.MethodInvocationContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import me.goudham.micronaut.trace.annotation.Trace;
import me.goudham.micronaut.trace.service.LoggingService;
import me.goudham.micronaut.trace.domain.LogType;

/**
 * Intercepts the annotated method and logs out when it
 * was entered and exited.
 *
 * @see Trace
 */
@Singleton
class TraceIntercepter extends Intercepter {

    @Inject
    TraceIntercepter(LoggingService loggingService) {
        super(loggingService);
    }

    /**
     * Logs before and after the method is called.
     * The method name and signature are included
     * within the log itself.
     *
     * @param context Object representing information about the annotated method
     * @return Result of annotated method after execution
     */
    @Override
    Object logAndExecuteMethod(MethodInvocationContext<Object, Object> context) {
        Object result;
        String name = context.getMethodName();
        String signature = getArguments(context);

        loggingService.trace(LogType.ENTERING, name, signature);
        try {
            result = context.proceed();
        } catch (Throwable throwable) {
            loggingService.trace(LogType.ERROR, name, signature);
            throw throwable;
        }
        loggingService.trace(LogType.EXITING, name, signature);

        return result;
    }
}