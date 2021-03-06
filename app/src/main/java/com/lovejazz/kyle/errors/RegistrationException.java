package com.lovejazz.kyle.errors;

public class RegistrationException extends Exception {

    private final RegistrationErrorCode errorCode;

    public RegistrationException(RegistrationErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    public RegistrationErrorCode getErrorCode() {
        return errorCode;
    }

}
