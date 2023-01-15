package me.goudham.micronaut.trace.shared;

import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.type.Argument;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.goudham.micronaut.trace.service.LoggingService;
import me.goudham.micronaut.trace.domain.LogType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@MicronautTest
class TimeIntercepterTest {
    @Inject
    private MethodInvocationContext<Object, Object> methodInvocationContext;

    @Inject
    private LoggingService loggingService;

    @Inject
    @Named("Time")
    private Intercepter sut;

    @Test
    void shouldNotProduceTraceLogsWhenTracingDisabled() {
        when(methodInvocationContext.getDeclaringType()).thenReturn(Object.class);
        when(loggingService.isTraceEnabled()).thenReturn(false);

        sut.intercept(methodInvocationContext);

        verify(methodInvocationContext, times(1)).getDeclaringType();
        verify(methodInvocationContext, times(1)).proceed();
        verifyNoMoreInteractions(methodInvocationContext);
    }

    @Test
    void successfullyLogMethodExecutionTime() {
        when(methodInvocationContext.getMethodName()).thenReturn("test");
        when(methodInvocationContext.getArguments()).thenReturn(new Argument[0]);

        sut.logAndExecuteMethod(methodInvocationContext);

        verify(loggingService, times(1)).trace(eq(LogType.EXECUTION_TIME), eq("test"), eq(""), anyLong());
        verifyNoMoreInteractions(loggingService);
    }

    @Test
    void shouldNotLogMethodExecutionTimeWhenException() {
        when(methodInvocationContext.getMethodName()).thenReturn("test");
        when(methodInvocationContext.getArguments()).thenReturn(new Argument[0]);
        RuntimeException actualException = new RuntimeException("Oh nyo~ Anyway");
        when(methodInvocationContext.proceed()).thenThrow(actualException);

        RuntimeException expectedException = assertThrows(RuntimeException.class, () -> sut.logAndExecuteMethod(methodInvocationContext));

        assertThat(actualException, is(expectedException));
        verifyNoInteractions(loggingService);
    }

    @MockBean(MethodInvocationContext.class)
    MethodInvocationContext<Object, Object> methodInvocationContext() {
        return mock(MethodInvocationContext.class);
    }

    @MockBean(LoggingService.class)
    LoggingService loggingService() {
        return mock(LoggingService.class);
    }
}