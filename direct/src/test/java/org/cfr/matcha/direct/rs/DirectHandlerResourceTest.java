package org.cfr.matcha.direct.rs;

import java.io.FileNotFoundException;

import javax.ws.rs.core.UriInfo;

import org.cfr.direct.testing.EasyMockTestCase;
import org.junit.Test;
import org.springframework.util.Log4jConfigurer;

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

        resource.setDirectManager(getMockFullDirectManager());
        replay();
        //resource.afterPropertiesSet();
        verify();

    }

    @Test
    public void handleFormUrlEncodedPostTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectManager(getMockFullDirectManager());

        String input = "myInput";
        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handleFormUrlEncodedPost(uriInfo, input);
        verify();
    }

    @Test
    public void handleJSONPostTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectManager(getMockFullDirectManager());

        String json = "myInput";
        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handleJSONPost(uriInfo, json);
        verify();
    }

    @Test
    public void handlePollGetTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectManager(getMockFullDirectManager());

        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handlePollGet(uriInfo);
        verify();
    }

    @Test
    public void handlePollPostTest() {
        DirectHandlerResource resource = new DirectHandlerResource();

        resource.setDirectManager(getMockFullDirectManager());

        UriInfo uriInfo = mock(UriInfo.class);

        replay();
        resource.handlePollPost(uriInfo);
        verify();
    }

}
