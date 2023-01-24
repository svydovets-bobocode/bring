package com.bobocode.svydovets.annotation.register;

import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;
import com.bobocode.svydovets.annotation.exception.NoSuchBeanException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//todo: we decided that bean registry only needs registerBean() and getRootContext()
public class DefaultBeanRegistry implements BeanRegistry {

    private static final Map<String, Object> rootContextMap = new ConcurrentHashMap<>();

    public DefaultBeanRegistry() {
    }

    @Override
    public void registerBean(String beanName, Object bean) {
        Object oldObject = rootContextMap.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException(
                    "Could not register object [" + oldObject + "] under bean name '" + beanName
                            + "': there is already object [" + oldObject + "] bound");
        }
        rootContextMap.put(beanName, bean);
    }

    @Override
    public Map<String, Object> getRootContext() {
        return rootContextMap;
    }

    //todo:remove it from parent interface and perform refactoring
    /**
     * Returns the bean instance by it`s unique name within the context.
     *
     * @param beanName bean ID by which it`s stored in the context
     * @param beanType type the bean will be casted to; can be an interface or superclass
     * @return an instance of the bean matching it`s name
     * @throws NoSuchBeanException if no bean of the given name was found
     */
    @Override
    public <T> T getSingleton(String beanName, Class<T> beanType) {
        return rootContextMap.entrySet().stream()
                .filter(beanEntry -> beanName.equals(beanEntry.getKey()))
                .findAny()
                .map(Map.Entry::getValue)
                .map(beanType::cast)
                .orElseThrow(NoSuchBeanException::new);
    }

    /**
     * Returns the bean instance that uniquely matches the given object type, if any.
     *
     * @param beanType type the bean must match; can be an interface or superclass
     * @return an instance of the single bean matching the required type
     * @throws NoSuchBeanException   if no bean of the given type was found
     * @throws NoUniqueBeanException if more than one bean of the given type was found
     */
    @Override
    public <T> T getSingleton(Class<T> beanType) {
        Map<String, T> matchingBeans = getAllSingletons(beanType);
        if (matchingBeans.size() > 1) {
            throw new NoUniqueBeanException();
        }
        return matchingBeans.values().stream()
                .findAny()
                .orElseThrow(NoSuchBeanException::new);
    }

    /**
     * Returns the map of all bean instances that match provided bean type.
     *
     * @param beanType type the beans must match; can be an interface or superclass
     * @return a map where key is a bean ID and value is a bean itself
     */
    @Override
    public <T> Map<String, T> getAllSingletons(Class<T> beanType) {
        return rootContextMap.entrySet().stream()
                .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }

}
