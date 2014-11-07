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
package org.cfr.matcha.direct.guice;

import org.cfr.matcha.MyAction;
import org.cfr.matcha.direct.BaseJaxRsRSDirectHandlerResourceTest;
import org.cfr.matcha.direct.DirectFactory;
import org.cfr.matcha.direct.InjectorWrapper;
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
