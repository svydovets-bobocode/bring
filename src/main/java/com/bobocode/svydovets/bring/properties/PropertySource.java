package com.bobocode.svydovets.bring.properties;

import java.util.Set;

public interface PropertySource<T> {
    String getName();

    T getSource();

    String getProperty(String name);

    Set<String> getAllPropertyNames();
}
