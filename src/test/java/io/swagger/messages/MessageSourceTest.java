package io.swagger.messages;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    private MessageSource messageSource;

    private static Stream<Arguments> argumentsStream() {
        return Stream.of(
                Arguments.of("error.constraint.create", "Record already exists. Please try updating the existing record."),
                Arguments.of("error.constraint.min", "Please check the input-values in your request-payload and try again."),
                Arguments.of("error.login.unauthorised", "Unauthorised Access. Please login and try again.")
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsStream")
    void givenMessageSource_whenRetrievingI18nClientMessages_thenFindAllErrors(String messageCode, String errorMsg) {
        final String message = messageSource.getMessage(messageCode, null, Locale.ENGLISH);
        assertNotNull(message);
        assertEquals(errorMsg, message);
    }

}
