package me.goudham.trace.interceptor;

import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.type.Argument;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@MicronautTest
class TraceInterpreterTest {
    private final TraceInterpreter sut;
    private final MethodInvocationContext<Object, Object> methodInvocationContext = methodInvocationContext();
    private final Logger logger = logger();

    @Inject
    public TraceInterpreterTest(TraceInterpreter sut) {
        this.sut = sut;
    }

    @Test
    void shouldNotProduceTraceLogs() {
        when(methodInvocationContext.getDeclaringType()).thenReturn(Object.class);

        sut.intercept(methodInvocationContext);

        verify(methodInvocationContext, times(1)).getDeclaringType();
        verify(methodInvocationContext, times(1)).proceed();
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

        sut.executeMethod(methodInvocationContext, logger);

        verify(logger, times(1)).trace("[ENTERING]: {}({})", "test", "java.lang.String winston, java.lang.Boolean pigeon");
        verify(logger, times(1)).trace("[EXITING]: {}({})", "test", "java.lang.String winston, java.lang.Boolean pigeon");
    }

    @Test
    void successfullyLogErrorWhenException() {
        when(methodInvocationContext.getMethodName()).thenReturn("test");
        when(methodInvocationContext.getArguments()).thenReturn(new Argument[0]);
        RuntimeException actualException = new RuntimeException("Uh Oh! Something Went Wrong!");
        when(methodInvocationContext.proceed()).thenThrow(actualException);

        RuntimeException expectedException = assertThrows(RuntimeException.class, () -> sut.executeMethod(methodInvocationContext, logger));

        assertThat(actualException, is(expectedException));
        verify(logger, times(1)).trace("[ERROR]: {}({})", "test", "");
    }

    @MockBean(MethodInvocationContext.class)
    MethodInvocationContext<Object, Object> methodInvocationContext() {
        return mock(MethodInvocationContext.class);
    }

    @MockBean(Logger.class)
    Logger logger() {
        return mock(Logger.class);
    }
}