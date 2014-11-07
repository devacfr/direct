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
package org.cfr.matcha.rs;

import java.io.FileNotFoundException;

import javax.ws.rs.core.UriInfo;

import org.cfr.commons.util.log.Log4jConfigurer;
import org.cfr.direct.testing.EasyMockTestCase;
import org.cfr.matcha.rs.IJaxRsDirectApplication;
import org.cfr.matcha.rs.JaxRsDirectResource;
import org.junit.Test;

public class DirectHandlerResourceTest extends EasyMockTestCase {

    static {
        try {
            Log4jConfigurer.initLogging("classpath:log4j.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private IJaxRsDirectApplication getMockFullDirectManager() {
        IJaxRsDirectApplication directManager = mock(IJaxRsDirectApplication.class);
        return directManager;
    }

    @Test
    public void initTest() throws Exception {
        JaxRsDirectResource resource = new JaxRsDirectResource();

        resource.setDirectApplication(getMockFullDirectManager());
        replay();
        //resource.afterPropertiesSet();
        verify();

    }

    @Test
    public void handleFormUrlEncodedPostTest() {
        JaxRsDirectResource resource = new JaxRsDirectResource();

        resource.setDirectApplication(getMockFullDirectManager());

        String input = "myInput";
        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handleFormUrlEncodedPost(uriInfo, input);
        verify();
    }

    @Test
    public void handleJSONPostTest() {
        JaxRsDirectResource resource = new JaxRsDirectResource();

        resource.setDirectApplication(getMockFullDirectManager());

        String json = "myInput";
        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handleJSONPost(uriInfo, json);
        verify();
    }

    @Test
    public void handlePollGetTest() {
        JaxRsDirectResource resource = new JaxRsDirectResource();

        resource.setDirectApplication(getMockFullDirectManager());

        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handlePollGet(uriInfo);
        verify();
    }

    @Test
    public void handlePollPostTest() {
        JaxRsDirectResource resource = new JaxRsDirectResource();

        resource.setDirectApplication(getMockFullDirectManager());

        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handlePollPost(uriInfo);
        verify();
    }

}
