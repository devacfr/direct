package org.cfr.matcha.direct.spring;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Named;

import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.direct.servlet.ServletDirectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.google.common.collect.Sets;

/**
 * 
 * @author devacfr
 * @since 1.0
 */
@Named("DirectApplication")
public class DefaultServletDirectContext extends ServletDirectContext implements BeanFactoryAware, InitializingBean {

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
        if (this.getDirectActions() == null || this.getDirectActions().isEmpty()) {
            Map<String, Object> beans = this.beanFactory.getBeansWithAnnotation(DirectAction.class);
            this.setActions(Sets.newHashSet(beans.values()));
        }
        this.init();
    }

}
