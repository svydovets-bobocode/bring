package com.bobocode.svydovets.autowiring.success;

import com.bobocode.svydovets.bring.annotations.Component;

@Component
public class SuccessMessageServiceImpl implements SuccessCustomService {
    public String getMessage() {
        return "Hello";
    }
}
