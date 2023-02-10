package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.Value;

@Component
public class SimpleValueBean {
    @Value("simpleAccountId")
    public String accountId;
}
