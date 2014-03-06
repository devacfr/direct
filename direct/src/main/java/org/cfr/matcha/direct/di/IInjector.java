package org.cfr.matcha.direct.di;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface IInjector {

    /**
     * Returns a service instance bound in the container for a specific type.
     */
    <T> T getInstance(String name, Class<T> type);

    /**
     * Returns a service instance bound in the container for a specific type.
     */
    <T> T getInstance(Class<T> type);

    /**
     * 
     * @param annotation
     * @return
     */
    Map<String, Object> getInstancesWithAnnotation(Class<? extends Annotation> annotation);

    /**
     * Performs field injection on a given object.
     */
    void injectMembers(Object object);

    <T> T register(T bean, String name);

    <T> T registerSingleton(Class<T> clazz);

    <T> T registerSingleton(Class<T> clazz, String name);

    <T> T register(Class<T> clazz, Scope scope);
}