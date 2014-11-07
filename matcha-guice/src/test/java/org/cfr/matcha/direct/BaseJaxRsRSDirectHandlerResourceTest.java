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
import org.cfr.matcha.rs.JaxRsDirectResource;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

public abstract class BaseJaxRsRSDirectHandlerResourceTest extends BaseJaxRsRSResourceTest {

    @Override
    protected Set<Class<?>> getJAXRSResourceToTest() {
        Set<Class<?>> rrcs = new HashSet<Class<?>>();
        rrcs.add(JaxRsDirectResource.class);
        return rrcs;
    }

    @Test
    public void handleJSONPostTest() throws Exception {

        ClientResource resource = getClient("/direct");

        Representation representation =
                resource.post(new StringRepresentation(
                        "{'action':'MyAction','method':'myMethod','data':['handleJSONPostTest'],'tid':4,'type':'rpc'}",
                        MediaType.APPLICATION_JSON));

        String response = IOUtils.toString(representation.getStream());
        LOGGER.debug(response);

    }

}
