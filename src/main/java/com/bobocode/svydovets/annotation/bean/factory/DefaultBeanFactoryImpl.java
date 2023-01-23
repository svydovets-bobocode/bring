package com.bobocode.svydovets.annotation.bean.factory;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.bean.processor.BeanPostProcessor;
import com.bobocode.svydovets.annotation.bean.processor.AutoSvydovetsBeanPostProcessor;
import com.bobocode.svydovets.annotation.register.BeanRegistry;
import com.bobocode.svydovets.annotation.register.DefaultBeanRegistry;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;

//todo: provide JavaDocs (optionally)
//todo: think about it`s purpose, do we need it? Can we merge it with ApplicationContext?
public class DefaultBeanFactoryImpl implements BeanFactory {

    private final BeanRegistry beanRegistry;
    private final BeanPostProcessor defaultBeanPostProcessor;

    public DefaultBeanFactoryImpl(Set<Class<?>> classes) {
        this.beanRegistry = new DefaultBeanRegistry();
        this.defaultBeanPostProcessor = new AutoSvydovetsBeanPostProcessor();
        initBeansRegistry(classes);
    }

    private void initBeansRegistry(Set<Class<?>> beanTypes) {
        for (Class<?> beanType : beanTypes) {
            try {
                Constructor<?> constructor = beanType.getConstructor();
                Object beanObject = constructor.newInstance();
                String beanName = resolveBeanName(beanType);
                beanRegistry.registerBean(beanName, beanObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        postProcessBeans();
    }

    private void postProcessBeans() {
        defaultBeanPostProcessor.process();
    }


    //todo: create class/service NameResolver
    private String resolveBeanName(Class<?> type) {
        String explicitName = type.getAnnotation(Component.class).value();
        return StringUtils.isBlank(explicitName) ? resolveTypeId(type) : explicitName;
    }

    private String resolveTypeId(Class<?> type) {
        String className = type.getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
    //

    //todo: add more error handling
    @Override
    public <T> T getBean(Class<T> beanType) {
        return beanRegistry.getSingleton(beanType);
    }

    @Override
    public <T> T getBean(Class<T> beanType, String name) {
        return beanRegistry.getSingleton(name, beanType);
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return beanRegistry.getAllSingletons(beanType);
    }

}
