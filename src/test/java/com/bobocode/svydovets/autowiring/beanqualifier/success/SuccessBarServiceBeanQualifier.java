package com.bobocode.svydovets.autowiring.beanqualifier.success;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.Qualifier;

@Component
public class SuccessBarServiceBeanQualifier {

    @Qualifier("fooService1")
    @AutoSvydovets
    SuccessFooServiceBeanQualifier fooService;

    public String getMessage() {
        return fooService.getMessage();
    }
}
