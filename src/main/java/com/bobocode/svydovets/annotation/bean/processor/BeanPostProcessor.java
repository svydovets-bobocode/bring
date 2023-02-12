package com.bobocode.svydovets.annotation.bean.processor;

/**
 * Interface that allows custom initialization processing of all beans in the
 * provided context. Logic should be provider by implementing
 * {@link #postProcessAfterInitialization(Object, String)} , {@link #postProcessBeforeInitialization(Object, String)}.
 */
public interface BeanPostProcessor {

    /**
     * Accepts instantiated bean as a parameter.
     * Executed after instantiated and before @PostConstruct.
     * <p>
     * Overridden method must not return null;
     *
     * @param bean
     * @param beanName
     * @return bean
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) {
      return bean;
    }

    /**
     * Accepts instantiated bean as a parameter.
     * Executed after @PostConstruct and {@link #postProcessBeforeInitialization(Object, String)}.
     * <p>
     * Overridden method must not return null;
     *
     * @param bean
     * @param beanName
     * @return bean
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) {
      return bean;
    }
}
