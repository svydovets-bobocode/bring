package com.bobocode.svydovets.bring.bean.processor;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Qualifier;
import com.bobocode.svydovets.bring.bean.processor.injector.Injector;

import java.lang.reflect.AccessibleObject;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link BeanProcessor} interface. Executes processing of
 * {@link AutoSvydovets} marked fields in beans. Such fields will be injected as a
 * dependency, retrieved from the context.
 * <p>
 * If no unique bean is present - processor will try to inject bean based on
 * {@link Qualifier} value.
 *
 * @see BeanProcessor
 * @see AutoSvydovets
 * @see Qualifier
 */
public class AutoSvydovetsBeanProcessor implements BeanProcessor {


    private final List<Injector<? extends AccessibleObject>> injectors;

    public AutoSvydovetsBeanProcessor(List<Injector<? extends AccessibleObject>> injectors) {
        this.injectors = injectors;
    }

    @Override
    public void processBeans(Map<String, Object> rootContext) {
        rootContext.values()
                .forEach(value -> {
                            injectors.forEach(injector -> {
                                        injector.injectDependency(value);
                                    }
                            );
                        }
                );
    }

}
