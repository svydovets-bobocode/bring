package com.bobocode.svydovets.annotation.bean.processor;

import com.bobocode.svydovets.annotation.annotations.Value;
import com.bobocode.svydovets.annotation.bean.factory.AnnotationBeanFactory;
import com.bobocode.svydovets.annotation.properties.PropertySources;
import com.bobocode.svydovets.annotation.register.BeanDefinition;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Arrays;

/**
 * The `ValueProcessor` class is a bean processor that processes the beans annotated with the `Value` annotation.
 * The class uses an instance of the {@link AnnotationBeanFactory} to retrieve the bean definitions and
 * an instance of the {@link PropertySources} to retrieve the property values.
 *
 */
public class ValueProcessor implements BeanProcessor {
    private final AnnotationBeanFactory beanFactory;
    private final PropertySources propertySources;

    /**
     * Creates a new instance of the `ValueProcessor` class.
     *
     * @param beanFactory     the {@link AnnotationBeanFactory} instance that retrieves the bean definitions
     * @param propertySources the {@link PropertySources} instance that retrieves the property values
     */
    public ValueProcessor(AnnotationBeanFactory beanFactory, PropertySources propertySources) {
        this.beanFactory = beanFactory;
        this.propertySources = propertySources;
    }

    /**
     * Processes the beans annotated with the `Value` annotation.
     * The method loops through the list of the bean names retrieved from the {@link AnnotationBeanFactory},
     * for each bean definition it loops through the list of the fields and checks if the field is annotated with
     * the `Value` annotation. If the field is annotated, the method retrieves the property value and sets it
     * to the field.
     *
     * @param rootContext a map that stores the objects representing the beans
     */
    public void processBeans(Map<String, Object> rootContext) {
        if (!propertyLoaded()) {
            return;
        }
        Set<String> beanNames = beanFactory.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

            try {
                Field[] fields = beanDefinition.getBeanClass().getDeclaredFields();

                // Loop through each field in the bean
                for (Field field : fields) {
                    Value valueAnnotation = field.getAnnotation(Value.class);
                    if (valueAnnotation != null) {
                        Object value = getPropertyValue(valueAnnotation.value(), field.getType());
                        if (value != null) {
                            field.setAccessible(true);
                            field.set(rootContext.get(beanName), value);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves the value of the property with the specified name.
     * The method uses the {@link PropertySources} instance to retrieve the property value.
     * If the property value is not found, the method returns `null`.
     *
     * @param propertyName the name of the property to retrieve the value for
     * @param clazz        the class of the property value
     * @param <T>          the type of the property value
     * @return the value of the property with the specified name or `null` if the property is not found
     */
    private <T> T getPropertyValue(String propertyName, Class<T> clazz) {
        if (!propertyName.matches("\\{.*?}")) {
            return clazz.cast(propertyName);
        }
        String propValue = propertySources.getPropertySources()
                .stream()
                .map(ps -> ps.getProperty(propertyName.replaceAll("\\{|}", "")))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if (propValue == null) {
            return null;
        }

        if (clazz.isAssignableFrom(List.class)) {
            return clazz.cast(Arrays.asList(propValue.split(",")));
        }

        return switch (clazz.getSimpleName()) {
            case "Long" -> clazz.cast(Long.valueOf(propValue));
            case "Integer" -> clazz.cast(Integer.valueOf(propValue));
            case "Double" -> clazz.cast(Double.valueOf(propValue));
            default -> clazz.cast(propValue);
        };
    }

    private boolean propertyLoaded() {
        return propertySources.getPropertySources().stream()
                .mapToLong(ps -> ps.getAllPropertyNames().size())
                .sum() > 0;
    }
}
