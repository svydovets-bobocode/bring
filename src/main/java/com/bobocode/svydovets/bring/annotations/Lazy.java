package com.bobocode.svydovets.bring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//todo():integrate with post processor (dealing with circular dependencies)
/**
 * Used among with {@link AutoSvydovets} in order to mark autowiring dependency as lazy one.
 * It means that it will be injected into the object only when it firstly will be accessed.
 * Used to avoid circular dependencies issues.
 *
 * @see AutoSvydovets
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface Lazy {
}
