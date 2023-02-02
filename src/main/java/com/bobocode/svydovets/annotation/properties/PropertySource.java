package com.bobocode.svydovets.annotation.properties;

import java.util.Set;

public interface PropertySource<T> {
    String getName();

    T getSource();

    String getProperty(String name);

    Set<String> getAllPropertyNames();
}
