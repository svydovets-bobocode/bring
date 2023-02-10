package com.bobocode.svydovets.autowiring.nosuchbean;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Component;

@Component
public class NoSuchBeanServiceImpl {
    @AutoSvydovets
    private NoSuchBeanService noSuchBeanService;
}
