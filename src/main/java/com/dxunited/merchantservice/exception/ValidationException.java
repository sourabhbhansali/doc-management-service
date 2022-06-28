package com.dxunited.merchantservice.exception;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    private String errorMessage;

    public ValidationException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
