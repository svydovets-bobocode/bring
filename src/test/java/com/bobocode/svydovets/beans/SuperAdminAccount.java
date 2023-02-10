package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.Value;

import java.util.List;

@Component
public class SuperAdminAccount {
    @Value("{roles}")
    public List<String> roles;
}
