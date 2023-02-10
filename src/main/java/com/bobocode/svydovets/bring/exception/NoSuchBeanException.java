package com.bobocode.svydovets.bring.exception;

/**
 * Exception is thrown in cases where requested bean is not present in the application context.
 */
public class NoSuchBeanException extends BeanException {
    private static final String NO_SUCH_BEAN_EXCEPTION_MESSAGE = """
            \tNo qualifying bean of type [%s] found for dependency:\s
            expected at least 1 bean which qualifies as autowire candidate for this dependency
            \tTo resolve the issue: use @Component or @Bean
            """;

    public NoSuchBeanException(String beanType) {
        super(String.format(NO_SUCH_BEAN_EXCEPTION_MESSAGE, beanType));
    }

}
