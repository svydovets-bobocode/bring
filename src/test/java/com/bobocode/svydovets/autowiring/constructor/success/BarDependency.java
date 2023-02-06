package com.bobocode.svydovets.autowiring.constructor.success;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.autowiring.constructor.ConstructorDependency;

@Component
public class BarDependency implements ConstructorDependency {
    @Override
    public String getMessage() {
        return "Bar";
    }
}
