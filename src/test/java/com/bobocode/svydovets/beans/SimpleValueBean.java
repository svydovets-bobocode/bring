package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Value;

@Component
public class SimpleValueBean {
    @Value("simpleAccountId")
    public String accountId;
}
