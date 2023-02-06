package com.bobocode.svydovets.properties;


import com.bobocode.svydovets.annotation.properties.PropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimplePropertySource implements PropertySource<Map<String, Object>> {
    private final Map<String, Object> properties = new HashMap<>();
    private final String name;

    public SimplePropertySource(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> getSource() {
        return properties;
    }

    @Override
    public String getProperty(String name) {
        return (String) properties.get(name);
    }

    @Override
    public Set<String> getAllPropertyNames() {
        return properties.keySet();
    }

    public void put(String name, Object value) {
        properties.put(name, value);
    }
}

