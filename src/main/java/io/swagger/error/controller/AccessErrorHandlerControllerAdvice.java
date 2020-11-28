package io.swagger.error.controller;

import io.swagger.error.Error;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestControllerAdvice
public class AccessErrorHandlerControllerAdvice extends AbstractControllerAdvice {

    public AccessErrorHandlerControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    /**
     * Authentication Credentials Not Found Exception
     *
     * @param authException {@link AuthenticationCredentialsNotFoundException}
     * @param req           {@link HttpServletRequest}
     * @return {@link ResponseEntity} with propagated error.
     */
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<Error> handleAuthenticationException(AuthenticationCredentialsNotFoundException authException, HttpServletRequest req) {
        String errorResp = extractMessageSource("error.login.unauthorised");
        log.error(authException.getMessage(), authException);
        log.info(errorResp);
        Error error = new Error()
                .code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(errorResp)
                .reason(authException.getMessage())
                .status(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .referenceError(req.getRequestURI());
        log.debug("Error Response: {}", error);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
