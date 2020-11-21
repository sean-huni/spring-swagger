package io.swagger.error.controller;

import org.springframework.context.MessageSource;

import java.util.Locale;


public abstract class AbstractControllerAdvice {
    private static final Locale LOCALE = Locale.ENGLISH;
    private final MessageSource messageSource;

    public AbstractControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    String extractMessageSource(final String sourceCode) {
        return messageSource.getMessage(sourceCode, null, LOCALE);
    }

}
