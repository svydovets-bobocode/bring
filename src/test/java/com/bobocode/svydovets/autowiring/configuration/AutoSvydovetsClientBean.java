package com.bobocode.svydovets.autowiring.configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AutoSvydovetsClientBean {

    final AutoSvydovetsDependency autoSvydovetsDependency;

    public String callAutoSvydovetsDependency() {
        return autoSvydovetsDependency.getMessage();
    }
}
