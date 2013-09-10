package org.cfr.direct.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.handler.impl.DirectHandler;
import org.cfr.direct.testing.EasyMockTestCase;
import org.easymock.EasyMock;
import org.junit.Test;

public class DirectHandlerResourceTest extends EasyMockTestCase {

    private DirectContext getMockFullDirectContext() {
        DirectContext directContext = mock(DirectContext.class);
        EasyMock.expect(directContext.getDirectHandlers()).andReturn(getMockDirectHandlers()).anyTimes();

        return directContext;
    }

    @Test
    public void initTest() throws Exception {
        DirectHandlerResource resource = new DirectHandlerResource();

        DirectContext directContext = getMockFullDirectContext();

        resource.setDirectContext(directContext);
        replay();
        resource.afterPropertiesSet();
        verify();

    }

    @Test
    public void handleFormUrlEncodedPostTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectContext(getMockFullDirectContext());

        String input = "myInput";
        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handleFormUrlEncodedPost(uriInfo, input);
        verify();
    }

    @Test
    public void handleJSONPostTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectContext(getMockFullDirectContext());

        String json = "myInput";
        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handleJSONPost(uriInfo, json);
        verify();
    }

    @Test
    public void handlePollGetTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectContext(getMockFullDirectContext());

        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handlePollGet(uriInfo);
        verify();
    }

    @Test
    public void handlePollPostTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectContext(getMockFullDirectContext());

        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handlePollPost(uriInfo);
        verify();
    }

    private List<IDirectHandler> getMockDirectHandlers() {
        DirectHandler handler = mock(DirectHandler.class);
        List<IDirectHandler> directHandlers = new ArrayList<IDirectHandler>();
        directHandlers.add(handler);
        return directHandlers;

    }
}
