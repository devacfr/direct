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
package org.cfr.matcha.direct;

import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;
import org.cfr.direct.testing.MockitoTestCase;
import org.junit.After;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxrs.JaxRsApplication;
import org.restlet.ext.jaxrs.ObjectFactory;
import org.restlet.resource.ClientResource;

public abstract class BaseJaxRsRSResourceTest extends MockitoTestCase {

    protected static final Logger LOGGER = Logger.getLogger(BaseJaxRsRSResourceTest.class);

    private int port = 64515;

    private String uriPattern = "/test";

    private String host;

    private Component component;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        startServer();
    }

    @After
    public void setDown() throws Exception {
        stopServer();
    }

    private class TestApplication extends Application {

        @Override
        public Set<Class<?>> getClasses() {
            return getJAXRSResourceToTest();
        }
    }

    protected abstract Set<Class<?>> getJAXRSResourceToTest();

    protected abstract ObjectFactory createObjectFactory();

    private void startServer() throws Exception {
        // create Component (as ever for Restlet)
        component = new Component();

        // The status service is disabled by default.
        component.getStatusService().setEnabled(false);

        Server server = component.getServers().add(Protocol.HTTP, port);

        // create JAX-RS runtime environment
        JaxRsApplication application = new JaxRsApplication(component.getContext());
        // attach Application
        Application testApplication = new TestApplication();
        application.add(testApplication);

        application.setObjectFactory(createObjectFactory());

        // Attach the application to the component and start it
        component.getDefaultHost().attach(uriPattern, application);
        component.start();

        LOGGER.debug("Server started on port " + server.getPort());
        host = "http://localhost:" + port + uriPattern;
        LOGGER.debug("Host defined as :" + host);

    }

    private void stopServer() throws Exception {

        LOGGER.debug("Stopping server");
        component.stop();
        LOGGER.debug("Server stopped");
    }

    protected ClientResource getClient(String path) {
        String uri = host + path;
        LOGGER.debug("ClientResource create with uri " + uri);
        return new ClientResource(host + path);
    }

}
