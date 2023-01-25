package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.bean.factory.AnnotationBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.bean.processor.AutoSvydovetsBeanPostProcessor;
import com.bobocode.svydovets.annotation.bean.processor.BeanPostProcessor;
import com.bobocode.svydovets.annotation.register.AnnotationRegistry;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

/**
 * Implementation of the {@link BeanFactory} and @{@link AnnotationRegistry} interfaces.
 * Creates an application context based on the scanned classes, that are marked by
 * {@link Component} annotation.
 * <p>
 * Creates a bean map, where key is bean ID, resolved by explicit {@link Component} value or
 * uncapitalized bean class name. After map creation performs chained call of {@link BeanPostProcessor}.
 *
 * @see AutoSvydovetsBeanPostProcessor
 * @see AnnotationRegistry
 * @see BeanFactory
 * @see BeanPostProcessor
 */
public class AnnotationApplicationContext extends AnnotationBeanFactory implements AnnotationRegistry {

    private List<BeanPostProcessor> beanPostProcessors;

    public AnnotationApplicationContext(String... packages) {
        initPostProcessors();
        Set<Class<?>> beanClasses = this.scan(packages);
        register(beanClasses.toArray(Class[]::new));
        beanPostProcessors.forEach(beanPostProcessor -> beanPostProcessor.processBeans(rootContextMap));
    }

    private void initPostProcessors() {
        BeanFactory beanFactory = new AnnotationBeanFactory();
        this.beanPostProcessors = List.of(new AutoSvydovetsBeanPostProcessor(beanFactory));
    }

    @Override
    public Set<Class<?>> scan(String... packages) {
        Reflections reflections = new Reflections((Object) packages);
        return reflections.getTypesAnnotatedWith(Component.class);
    }

    @Override
    public void register(Class<?>... componentClasses) {
        for (Class<?> beanType : componentClasses) {
            try {
                Constructor<?> constructor = beanType.getConstructor();
                Object beanObject = constructor.newInstance();
                String beanName = resolveBeanName(beanType);
                registerBean(beanName, beanObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void registerBean(String beanName, Object bean) {
        Object oldObject = rootContextMap.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException(
                    "Could not register object [" + oldObject + "] under bean name '" + beanName
                            + "': there is already object [" + oldObject + "] bound");
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
