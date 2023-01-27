package com.bobocode.svydovets.autowiring.nouniquebean.primaryduplicate;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Primary;

@Component
@Primary
public class SecondaryAnnotationService implements InvalidAnnotationService {

    @Override
    public String getServiceName() {
        return "SecondaryAnnotationService";
    }
}
