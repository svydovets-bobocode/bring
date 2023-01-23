package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.bean.factory.DefaultBeanFactoryImpl;
import com.bobocode.svydovets.support.AbstractApplicationContext;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

//todo: provide JavaDocs
public class AnnotationApplicationContext extends AbstractApplicationContext implements AnnotationRegistry {

    private final BeanFactory beanFactory;

    //todo: investigate how to get root package (implicitly, without providing)
    public AnnotationApplicationContext(String... packages) {
        Set<Class<?>> beanClasses = this.scan(packages);
        this.beanFactory = new DefaultBeanFactoryImpl(beanClasses);
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        return getBeanFactory().getBean(beanType);
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) {
        return getBeanFactory().getBean(beanType, name);
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return getBeanFactory().getAllBeans(beanType);
    }

    @Override
    public Set<Class<?>> scan(String... packages) {
        Reflections reflections = new Reflections((Object) packages);
        return reflections.getTypesAnnotatedWith(Component.class);
    }

    //todo: why? is it really needed
    @Override
    protected BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
