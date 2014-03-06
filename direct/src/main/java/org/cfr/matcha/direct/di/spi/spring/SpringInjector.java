package org.cfr.matcha.direct.di.spi.spring;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.cfr.matcha.direct.di.IInjector;
import org.cfr.matcha.direct.di.Scope;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

@Component()
@org.springframework.context.annotation.Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SpringInjector implements IInjector, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public <T> T getInstance(String name, Class<T> type) {
        return beanFactory.getBean(name, type);
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        return beanFactory.getBean(type);
    }

    @Override
    public void injectMembers(Object bean) {
        beanFactory.autowireBeanProperties(bean, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
        beanFactory.initializeBean(bean, bean.getClass().getName());

    }

    @Override
    public <T> T register(T bean, String name) {

        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) bean.getClass();
        String className = clazz.getCanonicalName();
        BeanDefinition beanDefinition = null;
        try {
            beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, className, clazz.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        beanFactory.registerBeanDefinition(name, beanDefinition);
        beanFactory.registerSingleton(name, bean);
        return bean;
    }

    @Override
    public <T> T registerSingleton(Class<T> clazz) {
        return registerComponentImplementation(clazz, clazz.getName(), Scope.singleton);
    }

    @Override
    public <T> T registerSingleton(Class<T> clazz, String name) {
        return registerComponentImplementation(clazz, name, Scope.singleton);
    }

    @Override
    public <T> T register(Class<T> clazz, Scope scope) {
        return registerComponentImplementation(clazz, clazz.getName(), scope);

    }

    @SuppressWarnings("unchecked")
    public <T> T registerComponentImplementation(Class<T> clazz, String name, Scope scope) {
        String className = clazz.getCanonicalName();
        // create the bean definition
        BeanDefinition beanDefinition = null;
        try {
            beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, className, clazz.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (scope.equals(Scope.singleton)) {
            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        } else {
            beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        }

        // Create the bean - I'm using the class name as the bean name
        beanFactory.registerBeanDefinition(name, beanDefinition);
        T bean = null;
        if (beanDefinition.getScope() == BeanDefinition.SCOPE_SINGLETON) {
            bean = beanFactory.getBean(name, clazz);
        } else {
            bean = (T) beanFactory.createBean(clazz, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
        }

        return bean;
    }

    @Override
    public Map<String, Object> getInstancesWithAnnotation(Class<? extends Annotation> annotation) {
        return this.beanFactory.getBeansWithAnnotation(annotation);
    }

}
