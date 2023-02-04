package com.bobocode.svydovets.bpp.success_change_field;

import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class BppComponent1WithFieldAnnotation {

    @ToNull
    private String string = "not null";

    public String getString() {
        return string;
    }
}
