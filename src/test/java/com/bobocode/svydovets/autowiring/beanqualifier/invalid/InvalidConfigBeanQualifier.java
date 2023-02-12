package com.bobocode.svydovets.autowiring.beanqualifier.invalid;

import com.bobocode.svydovets.annotation.annotations.Bean;
import com.bobocode.svydovets.annotation.annotations.Configuration;

@Configuration
public class InvalidConfigBeanQualifier {

    @Bean
    public InvalidFooServiceBeanQualifier fooService1() {
        InvalidFooServiceBeanQualifier fooService = new InvalidFooServiceBeanQualifier();
        fooService.setMessage("Foo1");
        return fooService;
    }

    @Bean
    public InvalidFooServiceBeanQualifier fooService2() {
        InvalidFooServiceBeanQualifier fooService = new InvalidFooServiceBeanQualifier();
        fooService.setMessage("Foo2");
        return fooService;
    }

}
