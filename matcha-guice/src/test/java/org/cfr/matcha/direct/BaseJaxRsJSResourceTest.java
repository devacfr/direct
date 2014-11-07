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

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.cfr.matcha.rs.JaxRsDirectJSResource;
import org.junit.Test;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public abstract class BaseJaxRsJSResourceTest extends BaseJaxRsRSResourceTest {

    @Override
    protected Set<Class<?>> getJAXRSResourceToTest() {
        Set<Class<?>> rrcs = new HashSet<Class<?>>();
        rrcs.add(JaxRsDirectJSResource.class);
        return rrcs;
    }

    @Test
    public void getFileDirectApiTest() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + JaxRsDirectJSResource.FILE_DIRECT_API);

        String response = IOUtils.toString(resource.get().getStream(), "UTF-8");

        LOGGER.debug(response);

        assertTrue(response.contains("Ext.namespace(\"App.Direct\");"));
        assertTrue(response.contains("App.Direct.PROVIDER_BASE_URL=window.location.protocol+\"//\"+window.location.host+\"/\"+(window.location.pathname.split(\"/\").length>2?window.location.pathname.split(\"/\")[1]+\"/\":\"\")+\"/direct\";"));
        assertTrue(response.contains("App.Direct.POLLING_URLS={};"));
        assertTrue(response.contains("App.Direct.REMOTING_API={url:App.Direct.PROVIDER_BASE_URL,type:\"remoting\",actions:{MyAction:[{name:\"myMethod\",len:1,formHandler:false}]}};"));
    }

    @Test
    public void getFileDirectDebugApiTest() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + JaxRsDirectJSResource.FILE_DIRECT_DEBUG_API);

        String response = IOUtils.toString(resource.get().getStream());

        LOGGER.debug(response);

        assertTrue(response.contains("Ext.namespace( 'App.Direct')"));
        assertTrue(response.contains("App.Direct.PROVIDER_BASE_URL=window.location.protocol + '//' + window.location.host + '/' + (window.location.pathname.split('/').length>2 ? window.location.pathname.split('/')[1]+ '/' : '')  + '/direct';"));
        assertTrue(response.contains("    MyAction: ["));
    }

    @Test
    public void getUnknownFileTest() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + "unknowfile");

        String response = IOUtils.toString(resource.get().getStream());
        LOGGER.debug(response);

        String expected = JaxRsDirectJSResource.NO_JS_FILE + "unknowfile";

        assertEquals(expected, response);
    }

    @Test(expected = ResourceException.class)
    public void getEmptyFileNameTest() throws Exception {

        ClientResource resource = getClient("/");

        resource.get();

    }

    @Test
    public void unknownFileGetTest() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + "unknowfile");

        String response = IOUtils.toString(resource.get().getStream());

        LOGGER.debug(response);

    }

}
