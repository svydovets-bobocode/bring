package com.bobocode.svydovets.bpp.success_change_field;

import com.bobocode.svydovets.bring.annotations.Component;

@Component
public class BppComponent2WithoutFieldAnnotation {

    private String string = "not null value";

    public String getString() {
        return string;
    }
}
