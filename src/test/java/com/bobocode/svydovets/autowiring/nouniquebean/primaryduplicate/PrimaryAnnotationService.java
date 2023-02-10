package com.bobocode.svydovets.autowiring.nouniquebean.primaryduplicate;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.Primary;

@Component
@Primary
public class PrimaryAnnotationService implements InvalidAnnotationService {
    @Override
    public String getServiceName() {
        return "PrimaryAnnotationService";
    }
}
