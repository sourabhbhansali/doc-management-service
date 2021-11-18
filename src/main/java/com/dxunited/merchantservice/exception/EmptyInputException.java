package com.dxunited.merchantservice.exception;

import org.springframework.stereotype.Component;

@Component
public class EmptyInputException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public EmptyInputException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public EmptyInputException() {
    }
}
