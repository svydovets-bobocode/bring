package com.bobocode.svydovets.bpp.success_change_field;

import com.bobocode.svydovets.annotation.bean.processor.BeanPostProcessor;
import com.bobocode.svydovets.annotation.exception.BeanException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class MyAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        List<Field> fields = Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ToNull.class))
                .toList();

        for (Field field : fields) {
            inject(bean, field);
        }

        return bean;
    }

    private void inject(Object bean, Field field) {
        try {
            field.setAccessible(true);
            field.set(bean, null);
        } catch (IllegalAccessException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }
}
