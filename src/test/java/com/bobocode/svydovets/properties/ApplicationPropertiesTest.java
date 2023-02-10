package com.bobocode.svydovets.properties;

import com.bobocode.svydovets.bring.properties.ApplicationPropertySource;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationPropertySourceTest {
    private static final String PROPERTIES_FILE = "test.properties";
    private static final String TEST_KEY = "test.key";
    private static final String TEST_VALUE = "test.value";

    @Test
    void testGetName() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource(PROPERTIES_FILE);
        assertEquals(PROPERTIES_FILE, propertySource.getName());
    }

    @Test
    void testGetSourceNotNull() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource(PROPERTIES_FILE);
        assertNotNull(propertySource.getSource());
    }

    @Test
    void testGetSourceNull() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource("not-exist.properties");
        assertNull(propertySource.getSource());
    }

    @Test
    void testGetProperty() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource(PROPERTIES_FILE);
        assertEquals(TEST_VALUE, propertySource.getProperty(TEST_KEY));
    }

    @Test
    void testGetAllPropertyNames() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource(PROPERTIES_FILE);
        Set<String> propertyNames = propertySource.getAllPropertyNames();
        assertTrue(propertyNames.contains(TEST_KEY));
    }


    @Test
    void givenPropertiesExist_whenGetSource_thenPropertiesShouldBeReturned() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource("test.properties");
        assertNotNull(propertySource.getSource());
    }

    @Test
    void givenPropertiesDoNotExist_whenGetSource_thenNullShouldBeReturned() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource("not_existing.properties");
        assertNull(propertySource.getSource());
    }

    @Test
    void givenPropertiesExist_whenGetProperty_thenCorrectValueShouldBeReturned() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource("test.properties");
        assertEquals("value1", propertySource.getProperty("key1"));
    }

    @Test
    void givenPropertiesExist_whenGetPropertyWithNotExistingKey_thenNullShouldBeReturned() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource("test.properties");
        assertNull(propertySource.getProperty("not_existing_key"));
    }

    @Test
    void givenPropertiesDoNotExist_whenGetProperty_thenNullShouldBeReturned() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource("not_existing.properties");
        assertNull(propertySource.getProperty("key1"));
    }

    @Test
    void givenPropertiesExist_whenGetAllPropertyNames_thenCorrectSetOfNamesShouldBeReturned() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource("test.properties");
        Set<String> propertyNames = propertySource.getAllPropertyNames();
        assertEquals(2, propertyNames.size());
        assertTrue(propertyNames.contains("test.key"));
        assertTrue(propertyNames.contains("key1"));
    }

    @Test
    void givenPropertiesDoNotExist_whenGetAllPropertyNames_thenEmptySetShouldBeReturned() {
        ApplicationPropertySource propertySource = new ApplicationPropertySource("not_existing.properties");
        Set<String> propertyNames = propertySource.getAllPropertyNames();
        assertTrue(propertyNames.isEmpty());
    }
}
