package com.bobocode.svydovets.autowiring.beanqualifier.invalid;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class InvalidBarServiceBeanQualifier {

    @AutoSvydovets
    InvalidFooServiceBeanQualifier fooService;

    public String getMessage() {
        return fooService.getMessage();
    }
}
