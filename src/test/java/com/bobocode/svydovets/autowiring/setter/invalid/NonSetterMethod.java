package com.bobocode.svydovets.autowiring.setter.invalid;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class NonSetterMethod {

    private Dependency dependency;

    @AutoSvydovets
    public void injectDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    public Dependency getDependency() {
        return dependency;
    }
}


