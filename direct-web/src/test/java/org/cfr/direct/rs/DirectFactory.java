package org.cfr.direct.rs;

import java.util.Map;

import org.restlet.ext.jaxrs.InstantiateException;
import org.restlet.ext.jaxrs.ObjectFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DirectFactory implements ObjectFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public <T> T getInstance(Class<T> jaxRsClass) throws InstantiateException {
        Map<String, T> beansMap = applicationContext.getBeansOfType(jaxRsClass);
        if (beansMap.size() != 1) {
            throw new IllegalStateException("One instance of " + jaxRsClass + " is expected found:" + beansMap.size());
        }
        return beansMap.values().iterator().next();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

}
