package org.cfr.matcha.direct.spring;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.matcha.direct.spi.BaseDirectContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

class ActionAnnotationBeanPostProcessor implements MergedBeanDefinitionPostProcessor, BeanFactoryAware {

    /**
     * 
     */
    private BeanFactory beanFactory;

    /**
     * 
     */
    // TODO [devacfr] create interface
    private final BaseDirectContext directContext;

    /**
     * 
     */
    private final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<Class<? extends Annotation>>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessMergedBeanDefinition(final RootBeanDefinition beanDefinition, final Class<?> beanType,
                                                final String beanName) {
    }

    /**
     * 
     */
    public ActionAnnotationBeanPostProcessor(@Nonnull final BaseDirectContext directContext) {
        this.directContext = directContext;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@Nullable final Object bean, @Nullable final String beanName) {
        if (isSupported(bean)) {
            directContext.registerAction(bean);
        }
        return bean;
    }

    private boolean isSupported(final Object bean) {
        return BaseDirectContext.isAction(bean);
    }

}