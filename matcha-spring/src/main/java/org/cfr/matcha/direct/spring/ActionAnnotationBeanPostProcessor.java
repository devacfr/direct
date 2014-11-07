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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.matcha.spi.BaseDirectContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

class ActionAnnotationBeanPostProcessor implements MergedBeanDefinitionPostProcessor, BeanFactoryAware {

    /**
     *
     */
    // TODO [devacfr] create interface
    private final BaseDirectContext directContext;

    /**
     *
     */
    public ActionAnnotationBeanPostProcessor(@Nonnull final BaseDirectContext directContext) {
        this.directContext = directContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanFactory(final BeanFactory beanFactory) {
        // noop
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessMergedBeanDefinition(final RootBeanDefinition beanDefinition, final Class<?> beanType,
            final String beanName) {
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