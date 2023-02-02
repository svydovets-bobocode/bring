package com.bobocode.svydovets.annotation.exception;

public class BeanException extends RuntimeException {
    public static final String BEAN_INSTANCE_MUST_NOT_BE_NULL
            = "Bean instance must not be null. (Check BeanPostProcessor not to return null)";

    public BeanException(String message) {
        super(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }
}
