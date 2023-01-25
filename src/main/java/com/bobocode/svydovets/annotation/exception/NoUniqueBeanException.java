package com.bobocode.svydovets.annotation.exception;

import com.bobocode.svydovets.annotation.annotations.Primary;
import com.bobocode.svydovets.annotation.annotations.Qualifier;

//todo: integrate with error handling, provide detailed messages and ways/tips to resolve the issue
/**
 * Exception is thrown in cases where there are several implementations of requested bean and
 * neither {@link Qualifier} not {@link Primary} are provided in order to resolve issue.
 */
public class NoUniqueBeanException extends RuntimeException {

}
