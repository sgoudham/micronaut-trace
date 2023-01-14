package me.goudham.trace.interceptor;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import jakarta.inject.Singleton;
import me.goudham.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

@Singleton
@InterceptorBean(Trace.class)
public class TraceInterpreter implements MethodInterceptor<Object, Object> {

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        Logger logger = LoggerFactory.getLogger(context.getDeclaringType());
        if (logger.isTraceEnabled()) {
            return executeMethod(context, logger);
        }
        return context.proceed();
    }

    protected Object executeMethod(MethodInvocationContext<Object, Object> context, Logger logger) {
        Object result;
        String methodName = context.getMethodName();
        String signature = Arrays.stream(context.getArguments())
            .map(argument -> argument.getTypeName() + " " + argument.getName())
            .collect(Collectors.joining(", "));

        logger.trace("[ENTERING]: {}({})", methodName, signature);
        try {
            result = context.proceed();
        } catch (Throwable throwable) {
            logger.trace("[ERROR]: {}({})", methodName, signature);
            throw throwable;
        }
        logger.trace("[EXITING]: {}({})", methodName, signature);

        return result;
    }
}