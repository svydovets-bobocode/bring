package com.bobocode.svydovets.annotation.exception;

public class NoUniqueBeanException extends BeanException {
    private static final String NO_UNIQUE_BEAN_EXCEPTION_MESSAGE = """
            \tNo qualifying bean of type [%s]: expected single matching bean but found %d: [%s]
            \tTo resolve the issue: use @Qualifier("...")
            """;

    public NoUniqueBeanException(String beanType, int found, String foundBeans) {
        super(String.format(NO_UNIQUE_BEAN_EXCEPTION_MESSAGE, beanType, found, foundBeans));
    }
}
