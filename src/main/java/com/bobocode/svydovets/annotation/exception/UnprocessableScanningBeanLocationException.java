package com.bobocode.svydovets.annotation.exception;

/**
 * Exception is thrown in cases where provided package(s) to scan beans is unprocessable(is null or any of provided items is empty).
 */
public class UnprocessableScanningBeanLocationException extends BeanException {

    public UnprocessableScanningBeanLocationException(String message) {
        super(message);
    }
}
