package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.Value;

@Component
public class AdminAccount {
    @Value("{accountIdNum}")
    public Long accountId;
}
