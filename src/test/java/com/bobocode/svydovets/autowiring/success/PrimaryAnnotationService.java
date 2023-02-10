package com.bobocode.svydovets.autowiring.success;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.Primary;

@Component
@Primary
public class PrimaryAnnotationService implements AnnotationService {
    @Override
    public String getServiceName() {
        return "PrimaryAnnotationService";
    }
}
