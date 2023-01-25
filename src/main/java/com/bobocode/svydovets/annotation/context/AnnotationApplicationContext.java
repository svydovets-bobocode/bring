package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.bean.factory.AnnotationBeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.bobocode.svydovets.annotation.bean.processor.AutoSvydovetsBeanPostProcessor;
import com.bobocode.svydovets.annotation.bean.processor.BeanPostProcessor;
import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.annotation.register.AnnotationRegistry;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

//todo: provide JavaDocs
public class AnnotationApplicationContext extends AnnotationBeanFactory implements AnnotationRegistry {

    private final BeanPostProcessor svydovetsBeanPostProcessor;

    //todo: investigate how to get root package (implicitly, without providing)
    public AnnotationApplicationContext(String... packages) {
        this.svydovetsBeanPostProcessor = new AutoSvydovetsBeanPostProcessor(this);
        Set<Class<?>> beanClasses = this.scan(packages);
        beanClasses.forEach(this::register);
        svydovetsBeanPostProcessor.processBeans(rootContextMap);
    }

    @Override
    public Set<Class<?>> scan(String... packages) {
        Reflections reflections = new Reflections((Object) packages);
        return reflections.getTypesAnnotatedWith(Component.class);
    }

    @Override
    public void register(Class<?>... componentClasses) {
        for (Class<?> beanType : componentClasses) {
            Constructor<?> constructor = getConstructor(beanType);
            Object bean = createInstance(constructor);
            String beanName = resolveBeanName(beanType);
            registerBean(beanName, bean);
        }
    }

    private Constructor<?> getConstructor(Class<?> beanType) {
        try {
            return beanType.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new BeanException("No default constructor for " + beanType.getName(), e);
        }
    }

    private Object createInstance(Constructor<?> constructor) {
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanException("Can't create instance", e);
        }
    }

    private void registerBean(String beanName, Object bean) {
        Object oldObject = rootContextMap.get(beanName);
        if (oldObject != null) {
            throw new BeanException(String.format(
                    "Could not register object [%s] under bean name '%s': there is already object [%s] bound",
                    oldObject, beanName, oldObject
            ));
        }
        rootContextMap.put(beanName, bean);
    }

    private String resolveBeanName(Class<?> type) {
        String explicitName = type.getAnnotation(Component.class).value();
        return StringUtils.isBlank(explicitName) ? resolveTypeId(type) : explicitName;
    }

    private String resolveTypeId(Class<?> type) {
        String className = type.getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}
