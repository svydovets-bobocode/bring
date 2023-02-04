package com.bobocode.svydovets.annotation.properties;

import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

public class ApplicationPropertySource implements PropertySource<Properties> {
    private Properties properties = null;
    private final String name;

    public ApplicationPropertySource(String name) {
        this.name = name;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(name)) {
            if (is != null) {
                properties = new Properties();
                properties.load(is);
            }
        } catch (Exception e) {
            // todo: add logging
            System.out.printf("Properties with name %s not found", name);
        }
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Properties getSource() {
        return properties;
    }

    @Override
    public String getProperty(String name) {
        return properties == null ? null : (String) properties.get(name);
    }

    @Override
    public Set<String> getAllPropertyNames() {
        return properties == null ? Collections.emptySet() : properties.stringPropertyNames();
    }
}
