package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.bring.annotations.Component;

@Component
public class BarFormatter implements Formatter {
    @Override
    public String format() {
        return "bar";
    }
}
