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
package org.cfr.matcha.direct.rs;

import java.io.FileNotFoundException;

import javax.ws.rs.core.UriInfo;

import org.cfr.commons.util.log.Log4jConfigurer;
import org.cfr.direct.testing.EasyMockTestCase;
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
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectApplication(getMockFullDirectManager());
        replay();
        //resource.afterPropertiesSet();
        verify();

    }

    @Test
    public void handleFormUrlEncodedPostTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectApplication(getMockFullDirectManager());

        String input = "myInput";
        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handleFormUrlEncodedPost(uriInfo, input);
        verify();
    }

    @Test
    public void handleJSONPostTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectApplication(getMockFullDirectManager());

        String json = "myInput";
        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handleJSONPost(uriInfo, json);
        verify();
    }

    @Test
    public void handlePollGetTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectApplication(getMockFullDirectManager());

        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handlePollGet(uriInfo);
        verify();
    }

    @Test
    public void handlePollPostTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectApplication(getMockFullDirectManager());

        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handlePollPost(uriInfo);
        verify();
    }

}
