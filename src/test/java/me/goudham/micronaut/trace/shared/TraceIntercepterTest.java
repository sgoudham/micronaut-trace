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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@MicronautTest
class TraceIntercepterTest {
    @Inject
    private MethodInvocationContext<Object, Object> methodInvocationContext;

    @Inject
    private LoggingService loggingService;

    @Inject
    @Named("Trace")
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
    void successfullyLogEnteringAndExiting() {
        when(methodInvocationContext.getMethodName()).thenReturn("test");
        when(methodInvocationContext.getArguments()).thenReturn(
            new Argument[]{
                Argument.of(String.class, "winston"),
                Argument.of(Boolean.class, "pigeon")
            }
        );

        sut.logAndExecuteMethod(methodInvocationContext);

        verify(loggingService, times(1)).trace(LogType.ENTERING, "test", "java.lang.String winston, java.lang.Boolean pigeon");
        verify(loggingService, times(1)).trace(LogType.EXITING, "test", "java.lang.String winston, java.lang.Boolean pigeon");
        verifyNoMoreInteractions(loggingService);
    }

    @Test
    void successfullyLogErrorWhenException() {
        when(methodInvocationContext.getMethodName()).thenReturn("test");
        when(methodInvocationContext.getArguments()).thenReturn(new Argument[0]);
        RuntimeException actualException = new RuntimeException("Oh nyo~ Anyway");
        when(methodInvocationContext.proceed()).thenThrow(actualException);

        RuntimeException expectedException = assertThrows(RuntimeException.class, () -> sut.logAndExecuteMethod(methodInvocationContext));

        assertThat(actualException, is(expectedException));
        verify(loggingService, times(1)).trace(LogType.ENTERING, "test", "");
        verify(loggingService, times(1)).trace(LogType.ERROR, "test", "");
        verifyNoMoreInteractions(loggingService);
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