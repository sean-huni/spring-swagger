package io.swagger.error.controller;

import io.swagger.error.Error;
import io.swagger.exception.unchecked.MissingPropertyException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;

@Log4j2
@RestControllerAdvice
public class ErrorHandlerRestControllerAdvice extends AbstractControllerAdvice {

    public ErrorHandlerRestControllerAdvice(MessageSource messageSource) {
        super(messageSource);
    }

    /**
     * With Spring Boot >= 2.4.0 this works as expected.
     *
     * @param cve {@link ConstraintViolationException}
     * @param req {@link HttpServletRequest}
     * @return {@link ResponseEntity} with propagated error.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<Error> handleConstraintException(ConstraintViolationException cve, HttpServletRequest req) {
        String errorResp = extractMessageSource("error.constraint.min");
        log.error(cve.getMessage(), cve);
        log.info(errorResp);
        ConstraintViolation<?> cv = cve.getConstraintViolations().stream().findFirst().orElseThrow(() -> new MissingPropertyException("Missing Constraint-Violations"));
        Error error = new Error()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(errorResp)
                .reason(Objects.nonNull(cv) ? cv.getMessage() : null)
                .field(Objects.nonNull(cv) && Objects.nonNull(cv.getPropertyPath()) ? cv.getPropertyPath().toString() : null)
                .status(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .referenceError(req.getRequestURI());
        log.debug("Error Response: {}", error);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }


    /**
     * With Spring <= 2.3.6.RELEASE, You cannot catch ConstraintViolationException.class because
     * it's not propagated to that layer of the code, it's caught by the lower layers, wrapped
     * and rethrown under {@link TransactionSystemException} type. In the end, the exception
     * that is thrown at the web layer is not a {@link ConstraintViolationException}.
     *
     * @param transactionSystemException {@link TransactionSystemException}
     * @param req                        {@link HttpServletRequest}
     * @return {@link ResponseEntity} with propagated error.
     */
    @Deprecated
    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<Error> handleTransactionSystemException(TransactionSystemException transactionSystemException, HttpServletRequest req) {
        String errorResp = extractMessageSource("error.constraint.min");
        ConstraintViolationException cve = new ConstraintViolationException(getConstraintViolations(transactionSystemException));
        log.error(cve.getMessage(), cve);
        log.info(errorResp);
        ConstraintViolation<?> cv = cve.getConstraintViolations().stream().findFirst().orElseThrow(() -> new MissingPropertyException("Missing Constraint-Violation Property Not Provided"));
        Error error = new Error()
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(errorResp)
                .reason(Objects.nonNull(cv) ? cv.getMessage() : null)
                .field(Objects.nonNull(cv) && Objects.nonNull(cv.getPropertyPath()) ? cv.getPropertyPath().toString() : null)
                .status(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .referenceError(req.getRequestURI());
        log.debug("Error Response: {}", error);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Extract the nested wrapped {@link ConstraintViolationException}.
     *
     * @param transactionSystemException {@link TransactionSystemException}.
     * @return {@link Set<ConstraintViolation>}.
     */
    @Deprecated
    private Set<ConstraintViolation<?>> getConstraintViolations(TransactionSystemException transactionSystemException) {
        return ((ConstraintViolationException) transactionSystemException.getCause().getCause()).getConstraintViolations();
    }

}
