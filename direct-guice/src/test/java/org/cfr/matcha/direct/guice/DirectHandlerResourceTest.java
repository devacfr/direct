package org.cfr.matcha.direct.guice;

import org.cfr.matcha.direct.BaseJaxRsRSDirectHandlerResourceTest;
import org.cfr.matcha.direct.DirectFactory;
import org.cfr.matcha.direct.InjectorWrapper;
import org.cfr.matcha.direct.MyAction;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.restlet.ext.jaxrs.ObjectFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

@RunWith(BlockJUnit4ClassRunner.class)
public class DirectHandlerResourceTest extends BaseJaxRsRSDirectHandlerResourceTest {

    private Injector injector;

    public DirectHandlerResourceTest() {
        injector = Guice.createInjector(new JaxRsDirectModule() {

            @Override
            protected void configure() {
                super.configure();
                for (Class<?> cls : getJAXRSResourceToTest()) {
                    bind(cls);
                }
            };

            @Override
            protected void configureActions() {
                this.bindAction().to(MyAction.class);
            }
        }.name("Direct").namespace("App"));
    }

    @Override
    protected ObjectFactory createObjectFactory() {
        DirectFactory objectFactory = new DirectFactory();
        objectFactory.setInjector(new InjectorWrapper() {

            @Override
            public <T> T getBeanOfType(Class<T> cls) {
                return injector.getInstance(cls);
            }
        });
        return objectFactory;
    }
}
