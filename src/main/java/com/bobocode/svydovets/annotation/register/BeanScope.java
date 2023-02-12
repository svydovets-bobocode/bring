package com.bobocode.svydovets.annotation.register;

/**
 * Enumeration represents the scope of a bean in a Bring-Svydovets application.
 * <p>
 * SINGLETON: The bean is a singleton, and a single instance of the bean will be created for the
 * entire application.
 * <p>
 * PROTOTYPE: The bean is a prototype, and a new instance of the bean will be created every time
 * it is requested.

 */
public enum BeanScope {
    SINGLETON,
    PROTOTYPE
}
