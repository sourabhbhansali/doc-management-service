package com.doc.management.exception;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int errorCode;
    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ValidationException() {
    }

    public ValidationException(int errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
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
