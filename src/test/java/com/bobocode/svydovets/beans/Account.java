package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Value;

@Component
public class Account {
    @Value("{accountId}")
    public String accountId;
}
