package com.bobocode.svydovets.annotation.bean.factory;

import com.bobocode.svydovets.annotation.exception.NoSuchBeanException;
import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;
import com.bobocode.svydovets.annotation.register.AnnotationRegistry;

import java.util.List;
import java.util.Map;

/**
 * Base interface that provides an ability to retrieve beans. All Application context
 * must explicitly or implicitly inherit it.
 * <p>
 * Methods should values based on context, provided by corresponding implementation.
 * It is no matter based on what configuration context was created, it should correctly
 * return beans by it`s contract.
 *
 * @see AnnotationRegistry
 */
public interface BeanFactory {
    /**
     * Returns the bean instance that uniquely matches the given object type, if any.
     *
     * @param beanType type the bean must match; can be an interface or superclass
     * @return an instance of the single bean matching the required type
     * @throws NoSuchBeanException   if no bean of the given type was found
     * @throws NoUniqueBeanException if more than one bean of the given type was found
     */
    <T> T getBean(Class<T> beanType);

    /**
     * Returns the bean instance by it`s unique name within the context.
     *
     * @param beanName bean ID by which it`s stored in the context
     * @param beanType type the bean will be casted to; can be an interface or superclass
     * @return an instance of the bean matching it`s name
     * @throws NoSuchBeanException if no bean of the given name was found
     */
    <T> T getBean(String beanName, Class<T> beanType);

    /**
     * Returns the map of all bean instances that match provided bean type.
     *
     * @param beanType type the beans must match; can be an interface or superclass
     * @return a map where key is a bean ID and value is a bean itself
     */
    <T> Map<String, T> getAllBeans(Class<T> beanType);


    /**
     * Returns the list of all bean instances that match provided bean type.
     *
     * @param beanType type the beans must match; can be an interface or superclass
     * @return a list of beans themselves
     */
    <T> List<T> getBeansByType(Class<T> beanType);
}
