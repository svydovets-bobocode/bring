package com.bobocode.svydovets.invalidbeans.defaultconstructor;

import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class InvalidBean {
    private final String name;

    public InvalidBean(String name) {
        this.name = name;
    }
}
