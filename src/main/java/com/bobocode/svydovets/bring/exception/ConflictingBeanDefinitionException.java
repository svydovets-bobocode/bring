package com.bobocode.svydovets.bring.exception;

//todo: throw this error when name resolver has two or more same names
/**
 * Exception is thrown during the context creation phase in situations
 * where two beans have the same ID (explicitly or implicitly provided).
 */
public class ConflictingBeanDefinitionException extends RuntimeException {
}
