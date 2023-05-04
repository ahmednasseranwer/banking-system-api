package com.banking.system.exception;

import com.banking.system.exception.custom.NotFoundException;
import com.banking.system.exception.custom.ProblemRunTimeException;
import com.bank.system.model.ErrorApiResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return handleExceptionInternal(ex, new ErrorApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return handleExceptionInternal(ex, new ErrorApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        return new ResponseEntity<>(new ErrorApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());

        return new ResponseEntity<>( new ErrorApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        return new ResponseEntity<>(new ErrorApiResponse(HttpStatus.CONFLICT.value(), ex.getConstraintViolations().toString()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 404
    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNoFoundException(final NotFoundException notFoundException) {
        logger.info(notFoundException.getClass().getName());

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProblemRunTimeException.class})
    protected ResponseEntity<Object> handleProblemRunTimeException(final ProblemRunTimeException problemRunTimeException) {
        logger.info(problemRunTimeException.getClass().getName());
        final ErrorApiResponse errorApiResponse = new ErrorApiResponse(problemRunTimeException.errorApiResponse.getCode(), problemRunTimeException.errorApiResponse.getMessage());

        return new ResponseEntity<>(errorApiResponse,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 404
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        return new ResponseEntity<>( new ErrorApiResponse(404, ex.getLocalizedMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // 405
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        return new ResponseEntity<>(new ErrorApiResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getLocalizedMessage()), new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        return new ResponseEntity<>( new ErrorApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()), new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    //409
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleSQLException(final DataIntegrityViolationException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);

        return new ResponseEntity<>( new ErrorApiResponse(HttpStatus.CONFLICT.value(), ex.getLocalizedMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);

        return new ResponseEntity<>(new ErrorApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
