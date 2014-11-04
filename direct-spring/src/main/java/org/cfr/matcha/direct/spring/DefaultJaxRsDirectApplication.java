/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.matcha.direct.spring;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.ws.rs.core.Application;

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
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 *
 * @author devacfr
 * @since 1.0
 */
@Named("DirectApplication")
public class DefaultJaxRsDirectApplication extends JaxRsDirectApplication implements BeanFactoryAware,
        InitializingBean, BeanDefinitionRegistryPostProcessor, ApplicationListener<ContextRefreshedEvent> {

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
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            // allowing the refresh. why not
            this.reset();
            // initialize direct context
            this.init();
        } catch (Exception e) {
            // TODO [devacfr] find better exception or message
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // not use

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.addBeanPostProcessor(new ActionAnnotationBeanPostProcessor(this));
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
