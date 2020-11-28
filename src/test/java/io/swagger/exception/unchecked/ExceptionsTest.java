package io.swagger.exception.unchecked;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionsTest {

    @Test
    void givenMissingPropertyException_whenInvoked_thenThrowMissingPropertyException() {
        MissingPropertyException propertyException = new MissingPropertyException("Test Missing Property Exception.");

        assertThatThrownBy(() -> {
            throw propertyException;
        })
                .isInstanceOf(MissingPropertyException.class)
                .hasCause(null)
                .descriptionText().equals("Test Missing Property Exception.");
    }

    @Test
    void givenSystemInitException_whenInvoked_thenThrowSystemInitException() {
        SystemInitException systemInitException = new SystemInitException("Test System Init Exception.");

        assertThatThrownBy(() -> {
            throw systemInitException;
        })
                .isInstanceOf(SystemInitException.class)
                .hasCause(null)
                .descriptionText().equals("Test System Init Exception.");
    }

}