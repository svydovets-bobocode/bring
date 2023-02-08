package com.bobocode.svydovets.autowiring.beanqualifier.success;

import com.bobocode.svydovets.annotation.annotations.Bean;
import com.bobocode.svydovets.annotation.annotations.Configuration;

@Configuration
public class SuccessConfigBeanQualifier {

    @Bean
    public SuccessFooServiceBeanQualifier fooService1() {
        SuccessFooServiceBeanQualifier fooService = new SuccessFooServiceBeanQualifier();
        fooService.setMessage("Foo1");
        return fooService;
    }

    @Bean
    public SuccessFooServiceBeanQualifier fooService2() {
        SuccessFooServiceBeanQualifier fooService = new SuccessFooServiceBeanQualifier();
        fooService.setMessage("Foo2");
        return fooService;
    }

}
