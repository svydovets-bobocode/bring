package com.bobocode.svydovets.autowiring.configuration;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Bean;
import com.bobocode.svydovets.annotation.annotations.Configuration;

@Configuration
public class TestConfig {

    @AutoSvydovets
    private AutoSvydovetsDependency autoSvydovetsDependency;

    @Bean
    public FooService fooService() {
        FooService fooService = new FooService();
        fooService.setMessage("Foo");
        return fooService;
    }

    @Bean
    public FooBarService fooBarService() {
        FooBarService fooBarService = new FooBarService(fooService());
        fooBarService.setMessage("Bar");
        return fooBarService;
    }

    @Bean
    public AutoSvydovetsClientBean autoSvydovetsClientBean() {
        return new AutoSvydovetsClientBean(autoSvydovetsDependency);
    }


}
