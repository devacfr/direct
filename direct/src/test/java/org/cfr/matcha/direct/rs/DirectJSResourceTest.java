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

import java.util.ArrayList;
import java.util.List;

import org.cfr.direct.testing.EasyMockTestCase;
import org.cfr.matcha.direct.spi.ConfigurationProvider;
import org.easymock.EasyMock;
import org.junit.Test;

import com.softwarementors.extjs.djn.api.RegisteredAction;
import com.softwarementors.extjs.djn.api.RegisteredApi;
import com.softwarementors.extjs.djn.api.RegisteredPollMethod;
import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;

public class DirectJSResourceTest extends EasyMockTestCase {

    @Test
    public void readFileTest() {
        DirectJSResource resource = new DirectJSResource();
        String expected = resource.readFile(DirectJSResource.PATH_DJN_REMOTE_CALL_SUPPORT);
        assertNotNull(expected);
    }

    /**
     * Try to read an unknow file
     */
    @Test(expected = IllegalStateException.class)
    public void readFileunknowfileTest() {
        DirectJSResource resource = new DirectJSResource();
        resource.readFile("unknowfile");
    }

    @Test
    public void retrieveNonExistingFileTest() {

        DirectJSResource resource = new DirectJSResource();
        String fileName = "unknowfile";
        String response = resource.getDjnJSResource(fileName);

        assertEquals(DirectJSResource.NO_JS_FILE + fileName, response);

    }

    @Test
    public void retrieveFILE_DJN_REMOTE_CALL_SUPPORT_Test() {

        DirectJSResource resource = new DirectJSResource();
        String fileName = DirectJSResource.FILE_DJN_REMOTE_CALL_SUPPORT;
        String response = resource.getDjnJSResource(fileName);

        String expected = resource.readFile(DirectJSResource.PATH_DJN_REMOTE_CALL_SUPPORT);
        assertEquals(expected, response);

    }

    @Test
    public void retrieveFILE_EJN_ASSERT_Test() {

        DirectJSResource resource = new DirectJSResource();
        String fileName = DirectJSResource.FILE_EJN_ASSERT;
        String response = resource.getDjnJSResource(fileName);

        String expected = resource.readFile(DirectJSResource.PATH_EJN_ASSERT);
        assertEquals(expected, response);

    }

    @Test
    public void retrieveFILE_DIRECT_API_Test() {

        RegisteredApi registeredApi = mock(RegisteredApi.class);
        EasyMock.expect(registeredApi.getApiNamespace()).andReturn("TestingApi").anyTimes();
        EasyMock.expect(registeredApi.getActionsNamespace()).andReturn(new String()).anyTimes();
        EasyMock.expect(registeredApi.getPollMethods()).andReturn(new ArrayList<RegisteredPollMethod>()).anyTimes();
        EasyMock.expect(registeredApi.getActions()).andReturn(new ArrayList<RegisteredAction>()).anyTimes();

        List<RegisteredApi> registeredApis = new ArrayList<RegisteredApi>();
        registeredApis.add(registeredApi);

        Registry registry = mock(Registry.class);
        EasyMock.expect(registry.getApis()).andReturn(registeredApis).anyTimes();

        GlobalConfiguration globalConfiguration = mock(GlobalConfiguration.class);

        ConfigurationProvider directConfiguration = mock(ConfigurationProvider.class);

        EasyMock.expect(directConfiguration.getGlobalConfiguration()).andReturn(globalConfiguration).anyTimes();

        IJaxRsDirectApplication directManager = mock(IJaxRsDirectApplication.class);

        DirectJSResource resource = new DirectJSResource();
        resource.setDirectApplication(directManager);

        String fileName = DirectJSResource.FILE_DIRECT_API;

        replay();

        resource.getDjnJSResource(fileName);

        verify();

    }

    @Test
    public void retrieveFILE_DIRECT_DEBUG_API_Test() {

        RegisteredApi registeredApi = mock(RegisteredApi.class);
        EasyMock.expect(registeredApi.getApiNamespace()).andReturn("TestingDebugApi").anyTimes();
        EasyMock.expect(registeredApi.getActionsNamespace()).andReturn(new String()).anyTimes();
        EasyMock.expect(registeredApi.getPollMethods()).andReturn(new ArrayList<RegisteredPollMethod>()).anyTimes();
        EasyMock.expect(registeredApi.getActions()).andReturn(new ArrayList<RegisteredAction>()).anyTimes();

        List<RegisteredApi> registeredApis = new ArrayList<RegisteredApi>();
        registeredApis.add(registeredApi);

        Registry registry = mock(Registry.class);
        EasyMock.expect(registry.getApis()).andReturn(registeredApis).anyTimes();

        GlobalConfiguration globalConfiguration = mock(GlobalConfiguration.class);

        ConfigurationProvider directConfiguration = mock(ConfigurationProvider.class);

        EasyMock.expect(directConfiguration.getGlobalConfiguration()).andReturn(globalConfiguration).anyTimes();

        IJaxRsDirectApplication directManager = mock(IJaxRsDirectApplication.class);

        DirectJSResource resource = new DirectJSResource();
        resource.setDirectApplication(directManager);

        String fileName = DirectJSResource.FILE_DIRECT_DEBUG_API;

        replay();

        resource.getDjnJSResource(fileName);

        verify();

    }

}
