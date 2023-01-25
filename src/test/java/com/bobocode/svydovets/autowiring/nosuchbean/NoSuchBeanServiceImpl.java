package com.bobocode.svydovets.autowiring.nosuchbean;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class NoSuchBeanServiceImpl {
    @AutoSvydovets
    private NoSuchBeanService noSuchBeanService;
}
