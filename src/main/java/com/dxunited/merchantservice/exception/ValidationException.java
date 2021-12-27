package com.dxunited.merchantservice.exception;

public class ValidationException extends RuntimeException{
    private static final long serialVersionUID = 1L;


    private String message;

    public ValidationException() {}

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidationException(String message){
        this.message=message;
    }


}
