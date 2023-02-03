package com.bobocode.svydovets.autowiring.constructor;

import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class BarDependency implements ConstructorDependency {
    @Override
    public String getMessage() {
        return "Bar";
    }
}
