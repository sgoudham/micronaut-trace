package me.goudham.trace.shared;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import me.goudham.trace.service.LoggingService;

import java.util.Arrays;
import java.util.stream.Collectors;

abstract class Intercepter implements MethodInterceptor<Object, Object> {
    final LoggingService loggingService;

    Intercepter(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    abstract Object logAndExecuteMethod(MethodInvocationContext<Object, Object> context);

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        loggingService.setLogger(context.getDeclaringType());
        if (loggingService.isTraceEnabled()) {
            return logAndExecuteMethod(context);
        }
        return context.proceed();
    }

    /**
     * {@link java.util.stream.Stream}'s over all method arguments,
     * joining the type and name together into one string.
     *
     * @param context Object representing information about the annotated method
     * @return {@code String} containing all method arguments separated by commas
     */
    String getArguments(MethodInvocationContext<Object, Object> context) {
        return Arrays.stream(context.getArguments())
            .map(argument -> argument.getTypeName() + " " + argument.getName())
            .collect(Collectors.joining(", "));
    }
}