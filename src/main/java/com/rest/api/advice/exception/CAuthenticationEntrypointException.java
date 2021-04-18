package com.rest.api.advice.exception;

public class CAuthenticationEntrypointException extends RuntimeException {
    public CAuthenticationEntrypointException() {
    }

    public CAuthenticationEntrypointException(String message) {
        super(message);
    }

    public CAuthenticationEntrypointException(String message, Throwable cause) {
        super(message, cause);
    }
}
