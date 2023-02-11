package com.bobocode.svydovets.annotation.exception;

/**
 * Exception is thrown during the context creation phase in situations
 * where two beans have the same ID (explicitly or implicitly provided).
 */
public class ConflictingBeanDefinitionException extends RuntimeException {
}
