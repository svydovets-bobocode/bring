package com.bobocode.svydovets.annotation.exception;

public class PostConstructProcessingException extends BeanException {

    public PostConstructProcessingException(String message) {
        super(message);
    }

    public PostConstructProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
