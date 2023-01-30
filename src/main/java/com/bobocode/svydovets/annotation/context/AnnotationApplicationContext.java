package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Primary;
import com.bobocode.svydovets.annotation.bean.factory.AnnotationBeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.bean.processor.AutoSvydovetsBeanProcessor;
import com.bobocode.svydovets.annotation.bean.processor.BeanProcessor;
import com.bobocode.svydovets.annotation.bean.processor.injector.ConstructorInjector;
import com.bobocode.svydovets.annotation.bean.processor.injector.FieldInjector;
import com.bobocode.svydovets.annotation.bean.processor.injector.Injector;
import com.bobocode.svydovets.annotation.bean.processor.injector.SetterInjector;
import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.annotation.exception.UnprocessableScanningBeanLocationException;
import com.bobocode.svydovets.annotation.register.AnnotationRegistry;
import com.bobocode.svydovets.annotation.register.BeanDefinition;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.util.Arrays;

/**
 * Implementation of the {@link BeanFactory} and @{@link AnnotationRegistry} interfaces.
 * Creates an application context based on the scanned classes, that are marked by
 * {@link Component} annotation.
 * <p>
 * Creates a bean map, where key is bean ID, resolved by explicit {@link Component} value or
 * uncapitalized bean class name. After map creation performs chained call of {@link BeanProcessor}.
 *
 * @see AutoSvydovetsBeanProcessor
 * @see AnnotationRegistry
 * @see BeanFactory
 * @see BeanProcessor
 */
public class AnnotationApplicationContext extends AnnotationBeanFactory implements AnnotationRegistry {

    private List<BeanProcessor> beanProcessors;
    private List<Injector<? extends AccessibleObject>> injectors;

    public AnnotationApplicationContext(String... packages) {
        initProcessors();
        Set<Class<?>> beanClasses = this.scan(packages);
        register(beanClasses.toArray(Class[]::new));
        beanProcessors.forEach(beanPostProcessor -> beanPostProcessor.processBeans(rootContextMap));
    }

    private void initProcessors() {
        initInjectors();
        this.beanProcessors = List.of(new AutoSvydovetsBeanProcessor(injectors));
    }

    private void initInjectors() {
       injectors = List.of(new SetterInjector(this),
               new FieldInjector(this),
               new ConstructorInjector(this));
    }

    @Override
    public Set<Class<?>> scan(String... packages) {
        validateScanArgument(packages);
        Reflections reflections = new Reflections((Object) packages);
        return reflections.getTypesAnnotatedWith(Component.class);
    }

    void validateScanArgument(String... packages) {
        if (packages == null) {
            throw new UnprocessableScanningBeanLocationException("Packages to scan argument can not be null");
        }
        if (Arrays.stream(packages).anyMatch(StringUtils::isBlank)) {
            throw new UnprocessableScanningBeanLocationException("Package to scan argument can not be null or empty");
        }
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
                    bean, beanName, oldObject
            ));
        }
        fillBeanDefinition(beanName, bean.getClass());
        rootContextMap.put(beanName, bean);
    }

    private void fillBeanDefinition(String beanName, Class<?> beanType) {
        var annotations = beanType.getAnnotations();
        boolean isPrimary = false;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Primary.class)) {
                isPrimary = true;
            }
        }

        var beanDefinition = BeanDefinition.builder()
                .beanClass(beanType)
                .beanName(beanName)
                .isPrimary(isPrimary)
//                .qualifier()
//                .scope()
//                .isLazy()
                .build();
        beanDefinitionMap.put(beanName, beanDefinition);
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
