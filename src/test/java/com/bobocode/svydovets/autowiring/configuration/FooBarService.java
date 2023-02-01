package com.bobocode.svydovets.autowiring.configuration;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class FooBarService {

    final FooService fooService;
    String message;

    public String getMessage() {
        return fooService.getMessage() + message;
    }
}
