package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Value;

@Component
public class AdminAccount {
    @Value("{accountIdNum}")
    public Long accountId;
    @Value("{adminSalary}")
    public Double adminSalary;
    @Value("{year}")
    public Integer year;
}
