package com.bobocode.svydovets.bring.properties;

import java.util.ArrayList;
import java.util.List;

public class PropertySources {

    private final List<PropertySource<?>> propertySources = new ArrayList<>();

    public void addLast(PropertySource<?> propertySource) {
        propertySources.add(propertySource);
    }

    public PropertySource<?> get(String name) {
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource.getName().equals(name)) {
                return propertySource;
            }
        }
        return null;
    }

    public List<PropertySource<?>> getPropertySources() {
        return propertySources;
    }

}
