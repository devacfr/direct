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

import javax.inject.Named;

import org.cfr.matcha.direct.servlet.ServletDirectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 *
 * @author devacfr
 * @since 1.0
 */
@Named("DirectApplication")
public class DefaultServletDirectContext extends ServletDirectContext implements BeanDefinitionRegistryPostProcessor,
ApplicationListener<ContextRefreshedEvent> {

    /**
     * log instance.
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            // allowing the refresh. why not
            this.reset();
            // Initialise direct context
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

}
