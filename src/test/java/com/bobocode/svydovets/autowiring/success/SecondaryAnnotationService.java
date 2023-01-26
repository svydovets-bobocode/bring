package com.bobocode.svydovets.autowiring.success;

import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class SecondaryAnnotationService implements AnnotationService {

    @Override
    public String getServiceName() {
        return "SecondaryAnnotationService";
    }
}
