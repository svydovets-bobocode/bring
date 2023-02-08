package com.bobocode.svydovets.annotation.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanException extends RuntimeException {
    public static final String BEAN_INSTANCE_MUST_NOT_BE_NULL
            = "Bean instance must not be null. (Check BeanPostProcessor not to return null)";
    public static final String CAN_NOT_CREATE_A_COPY_OF_BEAN
            = "Can not create an instance of bean for Prototype scope";

    public BeanException(String message) {
        super(message);
        log.error(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
        log.error(message);
    }
}
