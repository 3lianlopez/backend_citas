package com.software.citas.exception;

public class OtpLimitExceededException extends RuntimeException {

    public OtpLimitExceededException(String message) {
        super(message);
    }
}