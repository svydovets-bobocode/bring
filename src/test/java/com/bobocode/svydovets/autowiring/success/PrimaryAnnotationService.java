package com.bobocode.svydovets.autowiring.success;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Primary;
import com.bobocode.svydovets.autowiring.success.AnnotationService;

@Component
@Primary
public class PrimaryAnnotationService implements AnnotationService {
    @Override
    public String getServiceName() {
        return "PrimaryAnnotationService";
    }
}
