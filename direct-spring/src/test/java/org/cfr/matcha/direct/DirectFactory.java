package org.cfr.matcha.direct;

import org.restlet.ext.jaxrs.InstantiateException;
import org.restlet.ext.jaxrs.ObjectFactory;

public class DirectFactory implements ObjectFactory {

    private InjectorWrapper injector;

    @Override
    public <T> T getInstance(Class<T> jaxRsClass) throws InstantiateException {
        T bean = injector.getBeanOfType(jaxRsClass);
        if (bean == null) {
            throw new IllegalStateException("One instance of " + jaxRsClass + " is expected found: 0");
        }
        return bean;
    }

    public void setInjector(InjectorWrapper injector) {
        this.injector = injector;
    }

}
