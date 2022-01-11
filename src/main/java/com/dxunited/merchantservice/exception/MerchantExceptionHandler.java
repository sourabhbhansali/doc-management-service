package com.dxunited.merchantservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@ControllerAdvice
public class MerchantExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementException(NoSuchElementException noSuchElementException) {
        return new ResponseEntity<>("No value present in merchant database", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<String> mongoWriteException(MongoWriteException mongoWriteException) {
        return new ResponseEntity<>("Error occurred while writing into merchant DB, possibility duplicate key.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> jsonParseException(JsonProcessingException jsonProcessingException) {
        return new ResponseEntity<>("Input merchant not valid, failed to convert merchant string to json", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<String> executionException(ExecutionException executionException) {
        return new ResponseEntity<>("Exception occured while pushing merchant to kafka topic", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<String> interruptedException(InterruptedException interruptedException) {
        return new ResponseEntity<>("Exception occured while pushing merchant to kafka topic", HttpStatus.BAD_REQUEST);
    }
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return new ResponseEntity<>("Please change HTTP method type.", HttpStatus.NOT_FOUND);
    }
}