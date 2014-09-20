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
