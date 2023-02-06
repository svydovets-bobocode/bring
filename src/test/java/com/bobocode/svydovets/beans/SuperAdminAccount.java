package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Value;

import java.util.List;

@Component
public class SuperAdminAccount {
    @Value("{roles}")
    public List<String> roles;
}
