package com.bobocode.svydovets.bring.exception;

import com.bobocode.svydovets.bring.annotations.Primary;
import com.bobocode.svydovets.bring.annotations.Qualifier;

/**
 * Exception is thrown in cases where there are several implementations of requested bean and
 * neither {@link Qualifier} not {@link Primary} are provided in order to resolve issue.
 */
public class NoUniqueBeanException extends BeanException {
    private static final String NO_UNIQUE_BEAN_EXCEPTION_MESSAGE = """
            \tNo qualifying bean of type [%s]: expected single matching bean but found %d: [%s]
            \tTo resolve the issue: use @Qualifier("...")
            """;

    private static final String MORE_THAN_ONE_PRIMARY_FOUND = """
            \tNo qualifying bean of type [%s]: more than one 'primary' bean found among candidates: %s
            """;

    public NoUniqueBeanException(String beanType, int found, String foundBeans) {
        super(String.format(NO_UNIQUE_BEAN_EXCEPTION_MESSAGE, beanType, found, foundBeans));
    }

    public NoUniqueBeanException(String beanType, String foundBeans) {
        super(String.format(MORE_THAN_ONE_PRIMARY_FOUND, beanType, foundBeans));
    }
}
