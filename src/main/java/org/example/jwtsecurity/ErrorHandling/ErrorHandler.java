package org.example.jwtsecurity.ErrorHandling;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        String message = buildMessage(ex);
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(10)
                .errorMessage(message)
                .build();
        return new ResponseEntity<>(errorDto, status);
    }

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorDto handleResourceNotFoundException(ResourceAccessException exception) {
        return ErrorDto.builder()
                .errorCode(404)
                .errorMessage(exception.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorDto handleGeneralException(Exception ex) {
        // to-do: alert and write a relevant message
        return ErrorDto.builder()
                .errorCode(500)
                .errorMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorDto handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return ErrorDto.builder()
                .errorCode(401)
                .errorMessage(ex.getMessage())
                .build();
    }


    private String buildMessage(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return bindingResult.getFieldErrors()
                .stream()
                .map(this::toMessage)
                .collect(joining(", "));
    }

    private String toMessage(FieldError error) {
        return "Field '%s.%s' %s".formatted(error.getObjectName(), error.getField(), error.getDefaultMessage());
    }


}
