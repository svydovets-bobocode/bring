package com.bobocode.svydovets.autowiring.constructor.success;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.autowiring.constructor.ConstructorDependency;

@Component
public class FooDependency implements ConstructorDependency {
    @Override
    public String getMessage() {
        return "Foo";
    }
}
