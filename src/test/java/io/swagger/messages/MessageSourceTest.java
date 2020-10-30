package io.swagger.messages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MessageSourceTest {

    @Autowired
    private MessageSource messageSource;

    @Test
    @DisplayName("Record Data Violation Message Source")
    void givenMessageSource_whenRetrievingI18nClientMessages_thenFindCreateMessageClientInvalidError() {
        String message = messageSource.getMessage("error.constraint.create", null, Locale.ENGLISH);
        assertNotNull(message);
        assertEquals("Record already exists. Please try updating the existing record.", message);
    }

    @Test
    @DisplayName("Record Min Constraint Violation Message Source")
    void givenMessageSource_whenRetrievingI18nClientMessages_thenFindMinMessageClientInvalidError() {
        String message = messageSource.getMessage("error.constraint.min", null, Locale.ENGLISH);
        assertNotNull(message);
        assertEquals("Please check the input-values in your request-payload and try again.", message);
    }

}
