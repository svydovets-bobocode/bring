package com.bobocode.svydovets.annotation.bean.factory;

import java.util.Map;

//todo: @throws Exception are commented since checkstyle plugin fail build with unused import. Change rule?

public interface BeanFactory {
    /**
     * Returns the bean instance that uniquely matches the given object type, if any.
     *
     * @param beanType type the bean must match; can be an interface or superclass
     * @return an instance of the single bean matching the required type
//     * @throws NoSuchBeanException   if no bean of the given type was found
//     * @throws NoUniqueBeanException if more than one bean of the given type was found
     */
    <T> T getBean(Class<T> beanType);

    /**
     * Returns the bean instance by it`s unique name within the context.
     *
     * @param beanName bean ID by which it`s stored in the context
     * @param beanType type the bean will be casted to; can be an interface or superclass
     * @return an instance of the bean matching it`s name
//     * @throws NoSuchBeanException if no bean of the given name was found
     */
    <T> T getBean(String beanName, Class<T> beanType);

    /**
     * Returns the map of all bean instances that match provided bean type.
     *
     * @param beanType type the beans must match; can be an interface or superclass
     * @return a map where key is a bean ID and value is a bean itself
     */
    <T> Map<String, T> getAllBeans(Class<T> beanType);

}
