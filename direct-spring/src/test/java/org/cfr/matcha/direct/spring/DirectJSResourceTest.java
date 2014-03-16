package org.cfr.matcha.direct.spring;

import org.cfr.matcha.direct.BaseJaxRsJSResourceTest;
import org.cfr.matcha.direct.DirectFactory;
import org.cfr.matcha.direct.InjectorWrapper;
import org.junit.runner.RunWith;
import org.restlet.ext.jaxrs.ObjectFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:direct-jaxrs-test-beans-definition.xml" })
public class DirectJSResourceTest extends BaseJaxRsJSResourceTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    @Override
    protected ObjectFactory createObjectFactory() {
        DirectFactory objectFactory = new DirectFactory();
        objectFactory.setInjector(new InjectorWrapper() {

            @Override
            public <T> T getBeanOfType(Class<T> cls) {
                return applicationContext.getBean(cls);
            }
        });
        return objectFactory;
    }

}
