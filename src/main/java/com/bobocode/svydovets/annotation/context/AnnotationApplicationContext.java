package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.annotations.Bean;
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Configuration;
import com.bobocode.svydovets.annotation.annotations.Primary;
import com.bobocode.svydovets.annotation.bean.factory.AnnotationBeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.bean.processor.AutoSvydovetsBeanProcessor;
import com.bobocode.svydovets.annotation.bean.processor.BeanPostProcessor;
import com.bobocode.svydovets.annotation.bean.processor.BeanProcessor;
import com.bobocode.svydovets.annotation.bean.processor.injector.ConstructorInjector;
import com.bobocode.svydovets.annotation.bean.processor.injector.FieldInjector;
import com.bobocode.svydovets.annotation.bean.processor.injector.Injector;
import com.bobocode.svydovets.annotation.bean.processor.injector.SetterInjector;
import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.annotation.exception.UnprocessableScanningBeanLocationException;
import com.bobocode.svydovets.annotation.register.AnnotationRegistry;
import com.bobocode.svydovets.annotation.register.BeanDefinition;
import com.bobocode.svydovets.annotation.bean.processor.BeanPostProcessorScanner;
import com.bobocode.svydovets.annotation.util.LogoUtils;
import com.bobocode.svydovets.annotation.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.bobocode.svydovets.annotation.exception.BeanException.BEAN_INSTANCE_MUST_NOT_BE_NULL;

/**
 * Implementation of the {@link BeanFactory} and @{@link AnnotationRegistry} interfaces.
 * Creates an application context based on the scanned classes, that are marked either by
 * {@link Component} or {@link Configuration} annotation.
 * <p>
 * Creates a bean map, where key is bean ID, resolved by explicit {@link Component} or {@link Configuration} value or
 * uncapitalized bean class name. After map creation performs chained call of {@link BeanProcessor}.
 *
 * @see AutoSvydovetsBeanProcessor
 * @see AnnotationRegistry
 * @see BeanFactory
 * @see BeanProcessor
 */
@Slf4j
public class AnnotationApplicationContext extends AnnotationBeanFactory implements AnnotationRegistry {

    private BeanNameResolver beanNameResolver = new DefaultBeanNameResolver();
    private List<BeanPostProcessor> beanPostProcessors;
    private List<BeanProcessor> beanProcessors;
    private List<Injector<? extends AccessibleObject>> injectors;

    public AnnotationApplicationContext(String... packages) {
        log.info(LogoUtils.getBringLogo());
        initProcessors(packages);
        Set<Class<?>> beanClasses = this.scan(packages);
        register(beanClasses.toArray(Class[]::new));
        beanProcessors.forEach(beanPostProcessor -> beanPostProcessor.processBeans(rootContextMap));
    }

    private void initProcessors(String... packages) {
        initInjectors();
        this.beanProcessors = List.of(new AutoSvydovetsBeanProcessor(injectors));
        this.beanPostProcessors = new BeanPostProcessorScanner().scan(packages);
    }

    private void initInjectors() {
       injectors = List.of(new SetterInjector(this),
               new FieldInjector(this),
               new ConstructorInjector(this));
    }

    @Override
    public Set<Class<?>> scan(String... packages) {
        validateScanArgument(packages);
        Set<Class<?>> beanClasses = ReflectionUtils.scan(Component.class, packages);
        Set<Class<?>> configurationClasses = ReflectionUtils.scan(Configuration.class, packages);
        beanClasses.addAll(configurationClasses);
        return beanClasses;
    }

    //todo: add more tests for multiple packages
    private void validateScanArgument(String... packages) {
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
            String beanName = beanNameResolver.resolveBeanName(beanType);
            bean = processBean(bean, beanName);
            registerBean(beanName, bean);
            if (beanType.isAnnotationPresent(Configuration.class)) {
                registerMethodBasedBeans(bean, beanType);
            }
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
            throw new BeanException("Can't create instance ", e);
        }
    }

    private Object postProcessesBeforeInitialization(Object bean, String beanName) {
        for (BeanPostProcessor processor : this.beanPostProcessors) {
            bean = processor.postProcessBeforeInitialization(bean, beanName);
        }

        return bean;
    }

    private Object postProcessesAfterInitialization(Object bean, String beanName) {
        for (BeanPostProcessor processor : this.beanPostProcessors) {
            bean = processor.postProcessAfterInitialization(bean, beanName);
        }

        return bean;
    }

    private Object processBean(Object bean, String beanName) {
        bean = postProcessesBeforeInitialization(bean, beanName);
        //todo: here will be PostConstruct execution
        bean = postProcessesAfterInitialization(bean, beanName);
        return bean;
    }

    private void registerBean(String beanName, Object bean) {
        registerBean(beanName, bean, null);
    }

    private void registerBean(String beanName, Object bean, Method method) {
        if (bean == null) {
            throw new BeanException(BEAN_INSTANCE_MUST_NOT_BE_NULL);
        }

        Object oldObject = rootContextMap.get(beanName);
        if (oldObject != null) {
            throw new BeanException(String.format(
                    "Could not register object [%s] under bean name '%s': there is already object [%s] bound",
                    bean, beanName, oldObject
            ));
        }
        if (method == null) {
            Class<?> beanType = bean.getClass();
            fillBeanDefinition(beanName, beanType, beanType.getAnnotations());
        } else {
            fillBeanDefinition(beanName, method.getReturnType(), method.getAnnotations());
        }
        rootContextMap.put(beanName, bean);
    }

    private void fillBeanDefinition(String beanName, Class<?> beanType, Annotation[] annotations) {
        boolean isPrimary = isPrimary(annotations);
//        var beanScope = getBeanScope(beanType);

        var beanDefinition = BeanDefinition.builder()
                .beanClass(beanType)
                .beanName(beanName)
                .isPrimary(isPrimary)
//                .scope(beanScope)`
//                .qualifier()
//                .isLazy()
                .build();
        beanDefinitionMap.put(beanName, beanDefinition);
    }


    private static boolean isPrimary(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Primary.class)) {
                return true;
            }
        }
        return false;
    }

    private void registerMethodBasedBeans(Object object, Class<?> configurationType) {
        Arrays.stream(configurationType.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .forEach(method -> {
                    String beanName = beanNameResolver.resolveBeanName(method);
                    Object methodBean = invokeMethod(object, method);
                    methodBean = processBean(methodBean, beanName);
                    registerBean(beanName, methodBean, method);
                });
    }

    private static Object invokeMethod(Object object, Method method) {
        try {
            return method.getReturnType().cast(method.invoke(object));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanException("Can`t invoke method", e);
        }
    }

}
