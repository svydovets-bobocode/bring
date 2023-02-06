package com.bobocode.svydovets.autowiring.setter.invalid;

import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class Dependency {

    public String getMessage() {
        return "message";
    }
}
