package org.cfr.direct.testing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.handler.impl.DirectHandler;
import org.cfr.direct.rs.DirectHandlerResource;
import org.easymock.EasyMock;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;


public class DirectHandlerResourceTest extends BaseJAXRSResourceTest {

    private static final Logger LOGGER = Logger.getLogger(DirectHandlerResourceTest.class);

    private DirectContext directContext;

    @Override
    protected Set<Class<?>> getJAXRSResourceToTest() {
        Set<Class<?>> rrcs = new HashSet<Class<?>>();
        rrcs.add(DirectHandlerResource.class);
        return rrcs;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        directContext = mock(DirectContext.class);

        List<IDirectHandler> handlers = new ArrayList<IDirectHandler>();

        DirectHandler handler = mock(DirectHandler.class);
        handlers.add(handler);
        EasyMock.expect(directContext.getDirectHandlers()).andReturn(handlers);

    }

    //    handleFormUrlEncodedPost(String, UriInfo)
    //    handleJSONGet(UriInfo)
    //    handleJSONPost(UriInfo)
    //    handlePollGet(UriInfo)
    //    handlePollPost(UriInfo)

    @Test
    public void handleJSONPostTest() throws Exception {

        ClientResource resource = getClient("/directrs");

        Representation representation = resource.post(new StringRepresentation(
                "{'action':'MyAction','method':'myMethod','data':['handleJSONPostTest'],'tid':4,'type':'rpc'}", MediaType.APPLICATION_JSON));
        //String response = IOUtils.toString(resource.get().getStream());

        String response = IOUtils.toString(representation.getStream());
        LOGGER.info(response);

    }
}
