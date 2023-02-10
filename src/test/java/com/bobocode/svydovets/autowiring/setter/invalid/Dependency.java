package com.bobocode.svydovets.autowiring.setter.invalid;

import com.bobocode.svydovets.bring.annotations.Component;

@Component
public class Dependency {

    public String getMessage() {
        return "message";
    }
}
