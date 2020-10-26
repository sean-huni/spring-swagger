package io.swagger.error.controller;

import io.swagger.error.Error;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

@Log4j2
@RestControllerAdvice
public class ErrorHandlerRestControllerAdvice {
    private static final Locale locale = Locale.ENGLISH;
    private final MessageSource messageSource;

    public ErrorHandlerRestControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<Error> handleDataIntegrityException(DataIntegrityViolationException dataIntegrityViolationException) {
        String errorResp = extractMessageSource("error.constraint.create");
        log.error(dataIntegrityViolationException.getMessage(), dataIntegrityViolationException);
        log.info(errorResp);
        Error error = new Error()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(errorResp)
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        log.debug("Error Response: {}", error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<Error> handleConstraintException(TransactionSystemException transactionSystemException) {
        String errorResp = extractMessageSource("error.constraint.min");
        log.error(transactionSystemException.getMessage(), transactionSystemException);
        log.info(errorResp);
        Error error = new Error()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(errorResp)
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        log.debug("Error Response: {}", error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    private String extractMessageSource(final String sourceCode) {
        return messageSource.getMessage(sourceCode, null, locale);
    }
}
