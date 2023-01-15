package me.goudham.trace.shared;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import me.goudham.trace.annotation.Trace;

/**
 * Intercepts the annotated method and logs out when it
 * was entered and exited.
 *
 * @see Trace
 */
@Singleton
@InterceptorBean(Trace.class)
public class TraceInterpreter implements MethodInterceptor<Object, Object> {
    private final Intercepter traceIntercepter;

    @Inject
    public TraceInterpreter(@Named("Trace") Intercepter traceIntercepter) {
        this.traceIntercepter = traceIntercepter;
    }

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        return traceIntercepter.intercept(context);
    }
}