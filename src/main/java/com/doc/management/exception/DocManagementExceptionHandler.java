package com.doc.management.exception;

import com.doc.management.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DocManagementExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<GenericResponse> handleMaxSizeException(
            MaxUploadSizeExceededException maxUploadSizeExceededException) {
        GenericResponse response = GenericResponse.builder().success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message("File size cannot exceed 200 MB").build();
        return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<GenericResponse> validationException(ValidationException validationException) {
        GenericResponse response = GenericResponse.builder().success(false)
                .status(validationException.getErrorCode() != 0 ?
                        validationException.getErrorCode() : HttpStatus.BAD_REQUEST.value())
                .message(validationException.getErrorMessage()).build();
        return new ResponseEntity<GenericResponse>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleAllException(Exception e, WebRequest request) {
        GenericResponse response = GenericResponse.builder().success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
