package com.bobocode.svydovets.autowiring.success;

import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class SuccessMessageServiceImpl implements SuccessCustomService {
    public String getMessage() {
        return "Hello";
    }
}
