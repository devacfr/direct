package org.cfr.matcha.direct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.cfr.commons.util.ResourceUtils;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.impl.DirectHandler;
import org.cfr.matcha.direct.rs.DirectJSResource;
import org.cfr.matcha.direct.spi.BaseDirectContext;
import org.easymock.EasyMock;
import org.junit.Test;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public abstract class BaseJaxRsJSResourceTest extends BaseJaxRsRSResourceTest {

    private BaseDirectContext directContext;

    @Override
    protected Set<Class<?>> getJAXRSResourceToTest() {
        Set<Class<?>> rrcs = new HashSet<Class<?>>();
        rrcs.add(DirectJSResource.class);
        return rrcs;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        directContext = mock(BaseDirectContext.class);

        List<IDirectHandler> handlers = new ArrayList<IDirectHandler>();

        DirectHandler handler = mock(DirectHandler.class);
        handlers.add(handler);
        EasyMock.expect(directContext.getDirectHandlers()).andReturn(handlers);

    }

    @Test
    public void getFILE_EJN_ASSERT_Test() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + DirectJSResource.FILE_EJN_ASSERT);

        String response = IOUtils.toString(resource.get().getStream(), "UTF-8");

        LOGGER.debug(response);

        String expected = readFile(DirectJSResource.PATH_EJN_ASSERT);

        assertEquals(expected, response);
    }

    @Test
    public void getFILE_DJN_REMOTE_CALL_SUPPORT_Test() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + DirectJSResource.FILE_DJN_REMOTE_CALL_SUPPORT);

        String response = IOUtils.toString(resource.get().getStream(), "UTF-8");

        LOGGER.debug(response);

        String expected = readFile(DirectJSResource.PATH_DJN_REMOTE_CALL_SUPPORT);

        assertEquals(expected, response);
    }

    @Test
    public void getFILE_DIRECT_API_Test() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + DirectJSResource.FILE_DIRECT_API);

        String response = IOUtils.toString(resource.get().getStream(), "UTF-8");

        LOGGER.debug(response);

        assertTrue(response.contains("Ext.namespace(\"App.Direct\");"));
        assertTrue(response.contains("App.Direct.PROVIDER_BASE_URL=window.location.protocol+\"//\"+window.location.host+\"/\"+(window.location.pathname.split(\"/\").length>2?window.location.pathname.split(\"/\")[1]+\"/\":\"\")+\"/direct\";"));
        assertTrue(response.contains("App.Direct.POLLING_URLS={};"));
        assertTrue(response.contains("App.Direct.REMOTING_API={url:App.Direct.PROVIDER_BASE_URL,type:\"remoting\",actions:{MyAction:[{name:\"myMethod\",len:1,formHandler:false}]}};"));
    }

    @Test
    public void getFILE_DIRECT_DEBUG_API_Test() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + DirectJSResource.FILE_DIRECT_DEBUG_API);

        String response = IOUtils.toString(resource.get().getStream());

        LOGGER.debug(response);

        assertTrue(response.contains("Ext.namespace( 'App.Direct')"));
        assertTrue(response.contains("App.Direct.PROVIDER_BASE_URL=window.location.protocol + '//' + window.location.host + '/' + (window.location.pathname.split('/').length>2 ? window.location.pathname.split('/')[1]+ '/' : '')  + '/direct';"));
        assertTrue(response.contains("    MyAction: ["));
    }

    @Test
    public void getUNKNOW_FILE_Test() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + "unknowfile");

        String response = IOUtils.toString(resource.get().getStream());
        LOGGER.debug(response);

        String expected = DirectJSResource.NO_JS_FILE + "unknowfile";

        assertEquals(expected, response);
    }

    @Test(expected = ResourceException.class)
    public void getEmptyFileNameTest() throws Exception {

        ClientResource resource = getClient("/");

        resource.get();

    }

    @Test
    public void multipleGetTest() throws Exception {

        ClientResource resource = getClient("/direct/javascript/" + DirectJSResource.FILE_EJN_ASSERT);

        String response = IOUtils.toString(resource.get().getStream());

        LOGGER.debug(response);

        resource = getClient("/direct/javascript/" + DirectJSResource.FILE_DJN_REMOTE_CALL_SUPPORT);

        response = IOUtils.toString(resource.get().getStream());

        LOGGER.debug(response);

        resource = getClient("/direct/javascript/" + "unknowfile");

        response = IOUtils.toString(resource.get().getStream());

        LOGGER.debug(response);

    }

    private String readFile(String filePath) {
        InputStream stream = null;
        File file = null;
        try {
            file = ResourceUtils.getFile(filePath);
            stream = new FileInputStream(file);

            return IOUtils.toString(stream, "UTF-8");
        } catch (IOException ioe) {
            LOGGER.error(ExceptionUtils.getFullStackTrace(ioe));
            throw new IllegalStateException("Unable to load " + filePath, ioe);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

}
