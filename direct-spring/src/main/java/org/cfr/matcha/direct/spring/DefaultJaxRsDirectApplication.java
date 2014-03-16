package org.cfr.matcha.direct.spring;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.ws.rs.core.Application;

import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.direct.rs.IJaxRsDirectApplication;
import org.cfr.matcha.direct.rs.JaxRsDirectApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.google.common.collect.Sets;

/**
 * 
 * @author devacfr
 * @since 1.0
 */
@Named("DirectApplication")
public class DefaultJaxRsDirectApplication extends JaxRsDirectApplication implements BeanFactoryAware, InitializingBean {

    /**
     * log instance.
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Application app = this.get();
        // register direct handlers
        registerHandlers(app.getClasses());
        if (this.getDirectActions() == null || this.getDirectActions().isEmpty()) {
            Map<String, Object> beans = this.beanFactory.getBeansWithAnnotation(DirectAction.class);
            this.setActions(Sets.newHashSet(beans.values()));
        }
        this.init();
    }

    protected IJaxRsDirectApplication createApplication() {
        return new JaxRsDirectApplication();
    }

    protected void registerHandlers(Set<Class<?>> handlers) {
        for (Class<?> cls : handlers) {
            register(cls);
        }
    }

    private <T> T register(Class<T> clazz) {
        return register(clazz, clazz.getName());
    }

    private <T> T register(Class<T> clazz, String name) {
        String className = clazz.getCanonicalName();
        // create the bean definition
        BeanDefinition beanDefinition = null;
        try {
            beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(null, className, clazz.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);

        // Create the bean - I'm using the class name as the bean name
        beanFactory.registerBeanDefinition(name, beanDefinition);
        @SuppressWarnings("unchecked")
        T bean = (T) beanFactory.createBean(clazz, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

        return bean;
    }

}
