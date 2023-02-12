package com.bobocode.svydovets.annotation.properties;

import java.util.ArrayList;
import java.util.List;

public class PropertySources {

    private final List<PropertySource<?>> properties = new ArrayList<>();

    public void addLast(PropertySource<?> propertySource) {
        properties.add(propertySource);
    }

    public PropertySource<?> get(String name) {
        for (PropertySource<?> propertySource : properties) {
            if (propertySource.getName().equals(name)) {
                return propertySource;
            }
        }
        return null;
    }

    public List<PropertySource<?>> getPropertySources() {
        return properties;
    }

}
