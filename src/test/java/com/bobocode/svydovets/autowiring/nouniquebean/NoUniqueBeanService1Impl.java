package com.bobocode.svydovets.autowiring.nouniquebean;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class NoUniqueBeanService1Impl implements NoUniqueBeanService {
    @AutoSvydovets
    private NoUniqueBeanService noUniqueBeanService;
}
