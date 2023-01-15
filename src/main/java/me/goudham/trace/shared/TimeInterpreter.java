package me.goudham.trace.shared;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import me.goudham.trace.annotation.Time;

/**
 * Intercepts the annotated method and logs out the
 * execution time of the entire method.
 *
 * @see Time
 */
@Singleton
@InterceptorBean(Time.class)
public class TimeInterpreter implements MethodInterceptor<Object, Object> {
    private final Intercepter intercepter;

    @Inject
    public TimeInterpreter(@Named("Time") Intercepter intercepter) {
        this.intercepter = intercepter;
    }

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        return intercepter.intercept(context);
    }
}